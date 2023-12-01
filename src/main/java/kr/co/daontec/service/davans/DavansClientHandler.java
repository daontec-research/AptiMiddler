package kr.co.daontec.service.davans;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import kr.co.daontec.config.ConfigData;
import kr.co.daontec.dto.proto.DavansM09Broadcast;
import kr.co.daontec.service.DavansInit;
import kr.co.daontec.webclient.RequestApti;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
@Slf4j
public class DavansClientHandler extends ChannelInboundHandlerAdapter {
    private final DavansInit davansInit;
    private final RequestApti requestApti;
    private final ConfigData data;
    private final ByteBuf buf =
            ByteBufAllocator.DEFAULT.directBuffer().order(ByteOrder.LITTLE_ENDIAN);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        readAndProcessPacket(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                ctx.close();
            }
        }
    }

    @Override
    @SneakyThrows
    public void channelUnregistered(ChannelHandlerContext ctx) {
        log.error("예외발생 소켓종료");
        Thread.sleep(2000);
        ctx.close();
        davansInit.reRun();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void readAndProcessPacket(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            buf.writeBytes((ByteBuf) msg);
            do {
                if (!buf.isReadable(4)) {
                    break;
                }
                buf.markReaderIndex();

                int length = getPacketSize();
                if (length == -1) {
                    buf.clear();
                    break;
                }

                if (!buf.isReadable(length)) {
                    buf.resetReaderIndex();
                    break;
                }

                processBuffer(length, ctx);

            } while (flushBuffer());

        } finally {
            ((ByteBuf) msg).release();
        }
    }

    private int getPacketSize() {
        int length = buf.readInt();
        if (length <= 0 || length > (1024 * 10)) {
            return -1;
        }
        return length;
    }

    private void processBuffer(int length, ChannelHandlerContext ctx) throws Exception {
        String deviceId = buf.readCharSequence(8, StandardCharsets.UTF_8).toString();
        int cmd = buf.readInt();
        byte[] body = getPayload(length); //protobuf BODY자리

        messageHandler(cmd, body, deviceId, ctx);
    }

    private boolean flushBuffer() {
        if (buf.isReadable()) {
//            log.info("남은버퍼:{}", buf.readableBytes());
            return true;
        }
        buf.clear();
//        log.info("남은버퍼:{}", buf.readableBytes());
        return false;
    }

    private byte[] getPayload(int length) {
        byte[] payload = new byte[length - 12];
        buf.readBytes(payload);
        return payload;
    }

    private void messageHandler(int cmd, byte[] body, String deviceId, ChannelHandlerContext ctx) throws Exception {
        switch (cmd) {
//            case 910 -> m910Info(body, deviceId);
            case 906 -> handlerCarOut(body, deviceId, ctx);
            case 903 -> handlerCarIn(body, deviceId, ctx);
        }
    }

    private void handlerCarOut(byte[] body, String deviceId, ChannelHandlerContext ctx) throws Exception {
        try {
            DavansM09Broadcast.m0906_b_car_exit_complate m906 = DavansM09Broadcast.m0906_b_car_exit_complate.parseFrom(body);
            if (m906.getCustCd().equals("PP0102") || m906.getCustCd().equals("PP0103")) {
                log.info("장비번호 : {}, 출차차량번호 : {}", deviceId, m906.getCarNo());
//                requestApti.carOut(m906,deviceId);
            }
        } catch (Exception e) {
            log.error("ProtoBuf 파싱오류 : {}", e.getMessage());
            ctx.close();
        }
    }

    private void handlerCarIn(byte[] body, String deviceId, ChannelHandlerContext ctx) throws Exception {
        try {
            DavansM09Broadcast.m0903_b_car_entry_complate m903 = DavansM09Broadcast.m0903_b_car_entry_complate.parseFrom(body);
            if (m903.getCustCd().equals("PP0102") || m903.getCustCd().equals("PP0103")) {
                log.info("장비번호 : {}, 입차차량번호 : {}", deviceId, m903.getCarNo());
//                requestApti.carIn(m903,deviceId);
            }
        } catch (Exception e) {
            log.error("ProtoBuf 파싱오류 : {}", e.getMessage());
            ctx.close();
        }
    }
}
    /*private void m910Info(byte[] body, String deviceId) {
        try {
            DavansM09Broadcast.m0910_b_homenoti_info m0910 =
                    DavansM09Broadcast.m0910_b_homenoti_info.parseFrom(body);
            log.info(m0910.getHeader().getResultCd());
            log.info(m0910.getHeader().getResultMsg());
            log.info(m0910.getCarNo());
            log.info(m0910.getCustCd());
            log.info(m0910.getHouseholdInfo1());
            log.info(m0910.getHouseholdInfo2());
            log.info(m0910.getEventDtm());
        }catch (Exception e){
            log.error("ProtoBuf 파싱오류 : {}", e.getMessage());
        }
    }*/
