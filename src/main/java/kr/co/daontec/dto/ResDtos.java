package kr.co.daontec.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

public class ResDtos {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RegisterCar {
        private String carNo;
        private String regDtm;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReservationList {
        private Integer regNumber;
        private String carNo;
        private String startDt;
        private String endDt;
        private String reserveStatus;
        private String regDtm;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class InsertReservation{
        private String resultCd;
        private String resultMsg;
        private Integer regNumber;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Inout {
        private Integer regNumber;
        private String carNo;
        private String carType;
        private String type;
        private String regDtm;
    }
}
