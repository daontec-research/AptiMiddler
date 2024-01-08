package kr.co.daontec.webclient;

import jakarta.annotation.PostConstruct;
import kr.co.daontec.config.ConfigData;
import kr.co.daontec.dto.aptireq.EventInOut;
import kr.co.daontec.dto.aptiresponse.CarInOutRes;
import kr.co.daontec.dto.proto.DavansM09Broadcast;
import kr.co.daontec.service.davans.DavansHostConnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class RequestApti {
    private final ConfigData data;
    private final DavansHostConnect davansHostConnect;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        this.webClient = WebClient.builder()
                .baseUrl(data.getAptiUrl())
//                .defaultHeader("apiKey", data.getAptiKey())
                .build();
    }

    @Async
    public void carInOut(DavansM09Broadcast.m0903_b_car_entry_complate m903, String deviceId, String inout) throws IOException {

        CarInOutRes carInOutRes = webClient.post()
                .uri("/api/v1/parking/event/inout")
                .bodyValue(EventInOut.convertEventInOutDto(m903,inout))
                .retrieve()
                .bodyToMono(CarInOutRes.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error(e.getResponseBodyAsString());
                    return Mono.empty();
                })
                .block();

        log.info("아파트아이 입차전송결과 : {}", Objects.requireNonNull(carInOutRes).getResult_msg());
        davansHostConnect.sendData(m903, carInOutRes.getResult_cd().equals("00"), deviceId);

    }

    @Async
    public void carInOut(DavansM09Broadcast.m0906_b_car_exit_complate m906, String deviceId, String inout) throws IOException {

        CarInOutRes carInOutRes = webClient.post()
                .uri("/api/v1/parking/event/inout")
                .bodyValue(EventInOut.convertEventInOutDto(m906,inout))
                .retrieve()
                .bodyToMono(CarInOutRes.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error(e.getResponseBodyAsString());
                    return Mono.empty();
                })
                .block();

        log.info("아파트아이 출차전송결과 : {}", Objects.requireNonNull(carInOutRes).getResult_msg());
        davansHostConnect.sendData(m906, carInOutRes.getResult_cd().equals("00"), deviceId);

    }
}
    /*public void sendImage(String fileName, String binary) {

        Object object = webClient.post()
                .uri("/api/car/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromFormData(sendImageBodyMap(fileName, binary)))
                .retrieve()
                .bodyToMono(Object.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error(e.getResponseBodyAsString());
                    return Mono.empty();
                })
                .block();

        Map<String, String> resMap = (Map<String, String>) object;
        log.info("아파트아이 차번이미지전송결과 : {}", resMap.get("message"));
    }*/

    /*private MultiValueMap<String, String> carInBodyMap(DavansM09Broadcast.m0903_b_car_entry_complate m903) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("carNoInformation", m903.getCarNo());
        map.add("aptCode", data.getAptiCode());
        map.add("carInDate", m903.getInDtm().split(" ")[0]);
        map.add("carInTime", m903.getInDtm().split(" ")[1]);
        map.add("deviceCode", m903.getDeviceNm());
        map.add("reservationVisitCarId", String.valueOf(m903.getInCustRegId()));

        return map;
    }

    private MultiValueMap<String, String> carOutBodyMap(DavansM09Broadcast.m0906_b_car_exit_complate m906) {
        MultiValueMap<String, String> map  = new LinkedMultiValueMap<>();
        map.add("carNoInformation", m906.getCarNo());
        map.add("aptCode", data.getAptiCode());
        map.add("carOutDate", m906.getOutDtm().split(" ")[0]);
        map.add("carOutTime", m906.getOutDtm().split(" ")[1]);
        map.add("deviceCode", m906.getOutDeviceNm());

        return map;
    }

    private MultiValueMap<String, String> sendImageBodyMap(String fileName, String binary) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("fileName", fileName);
        map.add("file", binary);
        map.add("aptCode", data.getAptiCode());

        return map;
    }

    private void loadImage(String url) throws IOException {
        //String fileName = Arrays.stream(url.split("/"))
        //        .reduce((first, second) -> second)
        //        .orElse("");

        String[] list = url.split("/");
        String fileName = list[list.length - 1];

        byte[] imageBytes = downloadImage(url);
        String imageString = new String(imageBytes, StandardCharsets.UTF_8);

        sendImage(fileName, imageString);
    }

    private static byte[] downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        String[] tempArray = imageUrl.split("\\.");
        String suffix = tempArray[tempArray.length - 1];

        try (InputStream in = url.openStream()) {
            // 이미지를 현재 작업 디렉토리에 저장
            Path tempFile = Files.createTempFile(UUID.randomUUID().toString(), suffix);
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);

            byte[] imageBytes = Files.readAllBytes(tempFile);

            Files.delete(tempFile);

            return imageBytes;
        }
    }*/
