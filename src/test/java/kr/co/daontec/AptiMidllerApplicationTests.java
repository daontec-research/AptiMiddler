package kr.co.daontec;

import kr.co.daontec.dto.AptiResponse;
import kr.co.daontec.dto.Parameters;
import kr.co.daontec.repository.SPCall;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@SpringBootTest
class AptiMidllerApplicationTests {

    @Autowired
    SPCall spCall;

    //    @Test
    void registerCar() {
        System.out.println(spCall.registerCar(Parameters.RegisterCar.builder()
                .dong("0103")
                .ho("0102")
                .pageNumber(1)
                .countPerPage(10)
                .build()));
    }

    //    @Test
    void reservationList() {
        System.out.println(spCall.reservationList(Parameters.ReservationList.builder()
                .dong("0101")
                .ho("0102")
                .startDt("2023-10-01")
                .endDt("2023-11-30")
                .pageNumber(1)
                .countPerPage(10)
                .build()));
    }

    //    @Test
    void reservation() {
        System.out.println(spCall.reservation(Parameters.Reservation.builder()
                .dong("0101")
                .ho("0103")
                .carNo("41오0845")
                .startDt("2023-11-15")
                .endDt("2023-11-16")
                .build()));
    }

    //    @Test
    void delReservation() {
        System.out.println(spCall.delReservation(Parameters.DelReservation.builder()
                .dong("0101")
                .ho("0103")
                .regNumber(13)
                .build()));
    }

    //    @Test
    void inOut() {
        System.out.println(spCall.inOut(Parameters.Inout.builder()
                .dong("0101")
                .ho("0102")
                .carType("")
                .pageNumber(1)
                .countPerPage(10)
                .build()));
    }

//    @Test
    void sendimage() throws IOException {
        String url = "http://192.168.0.158:8000/pgi/2023/11/30/LPR_01010101_161504230_2842.jpg";

        try {
            byte[] imageBytes = downloadImage(url);
            String imageString = new String(imageBytes, StandardCharsets.UTF_8);
            System.out.println(imageString);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }
}
