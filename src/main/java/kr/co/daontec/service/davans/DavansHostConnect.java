package kr.co.daontec.service.davans;


import kr.co.daontec.dto.proto.DavansCommon;
import kr.co.daontec.dto.proto.DavansM02Entry;
import kr.co.daontec.dto.proto.DavansM09Broadcast;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class DavansHostConnect {
    @Value("${davans.host}")
    private String host;
    private final ByteBuffer buffer = ByteBuffer.allocate(9999);

    @Async
    public void sendData(Object reqBody, boolean result,String devId) throws IOException {

        Socket socket = new Socket(host, 8801);
        socket.setSoTimeout(6000);

        OutputStream os = socket.getOutputStream();
        InputStream is = socket.getInputStream();

        DavansPacket davansPacket = new DavansPacket(221, devId);

        davansPacket.fnsetbodyinfo(m0221Builder(reqBody, result).toByteArray());
        os.write(davansPacket.get_packet());

        byte[] data = new byte[9999];
        int bufferSize = is.read(data);
        byte[] readBytes = new byte[bufferSize];
        System.arraycopy(data, 0, readBytes, 0, bufferSize);
        buffer.put(readBytes);
        buffer.flip();

        int payloadSize = getPayloadSize();
        String deviceId = getDeviceId();
        int cmd = getCmd();
        byte[] body = getPayload(payloadSize);

        try {
            DavansM02Entry.m0222_homenoti_info_res m222 = DavansM02Entry.m0222_homenoti_info_res.parseFrom(body);
            log.info("다반스 HOST 세대통보결과 : {}", m222.getHeader().getResultMsg());
        } catch (Exception e) {
            log.error("ProtoBuf 파싱오류 : {}", e.getMessage());
        } finally {
            socket.close();
            buffer.clear();
        }

    }


    private int getPayloadSize() {
        return buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private String getDeviceId() {
        byte[] devId = new byte[8];
        buffer.get(devId, 0, 8);
        return new String(devId, StandardCharsets.UTF_8);
    }

    private int getCmd() {
        return buffer.order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private byte[] getPayload(int length) {
        byte[] payload = new byte[length - DavansPacket.getHEADER_SIZE()];
        buffer.get(payload, 0, length - DavansPacket.getHEADER_SIZE());
        return payload;
    }

    private DavansM02Entry.m0221_homenoti_info_req m0221Builder(
            Object body,
            boolean result
    ) {
        if (body instanceof DavansM09Broadcast.m0903_b_car_entry_complate) {
            return detailBuilder((DavansM09Broadcast.m0903_b_car_entry_complate) body, result);
        }
        return detailBuilder((DavansM09Broadcast.m0906_b_car_exit_complate) body, result);
    }

    private DavansM02Entry.m0221_homenoti_info_req detailBuilder(DavansM09Broadcast.m0903_b_car_entry_complate reqBody, boolean result) {
        if (result) {
            return DavansM02Entry.m0221_homenoti_info_req.newBuilder()
                    .setHeader(DavansCommon.pb_header_req.newBuilder()
                            .setVer("1.0")
                            .build())
                    .setStateCd("00")
                    .setStateMsg("정상")
                    .setCarNo(reqBody.getCarNo())
                    .setCustCd(reqBody.getCustCd())
                    .setHouseholdInfo1(reqBody.getHouseholdInfo1())
                    .setHouseholdInfo2(reqBody.getHouseholdInfo2())
                    .setEventDtm(reqBody.getInDtm())
                    .build();
        }
        return DavansM02Entry.m0221_homenoti_info_req.newBuilder()
                .setHeader(DavansCommon.pb_header_req.newBuilder()
                        .setVer("1.0")
                        .build())
                .setStateCd("42")
                .setStateMsg("홈넷 데이터 수신 오류")
                .setCarNo(reqBody.getCarNo())
                .setCustCd(reqBody.getCustCd())
                .setHouseholdInfo1(reqBody.getHouseholdInfo1())
                .setHouseholdInfo2(reqBody.getHouseholdInfo2())
                .setEventDtm(reqBody.getInDtm())
                .build();
    }

    private DavansM02Entry.m0221_homenoti_info_req detailBuilder(DavansM09Broadcast.m0906_b_car_exit_complate reqBody, boolean result) {
        if (result) {
            return DavansM02Entry.m0221_homenoti_info_req.newBuilder()
                    .setHeader(DavansCommon.pb_header_req.newBuilder()
                            .setVer("1.0")
                            .build())
                    .setStateCd("00")
                    .setStateMsg("정상")
                    .setCarNo(reqBody.getCarNo())
                    .setCustCd(reqBody.getCustCd())
                    .setHouseholdInfo1(reqBody.getHouseholdInfo1())
                    .setHouseholdInfo2(reqBody.getHouseholdInfo2())
                    .setEventDtm(reqBody.getOutDtm())
                    .build();
        }
        return DavansM02Entry.m0221_homenoti_info_req.newBuilder()
                .setHeader(DavansCommon.pb_header_req.newBuilder()
                        .setVer("1.0")
                        .build())
                .setStateCd("42")
                .setStateMsg("홈넷 데이터 수신 오류")
                .setCarNo(reqBody.getCarNo())
                .setCustCd(reqBody.getCustCd())
                .setHouseholdInfo1(reqBody.getHouseholdInfo1())
                .setHouseholdInfo2(reqBody.getHouseholdInfo2())
                .setEventDtm(reqBody.getOutDtm())
                .build();
    }
}
