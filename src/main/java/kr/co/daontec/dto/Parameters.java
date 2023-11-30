package kr.co.daontec.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

public class Parameters {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RegisterCar {
        private String dong;
        private String ho;
        private Integer pageNumber;
        private Integer countPerPage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReservationList {
        private String dong;
        private String ho;
        private String startDt;
        private String endDt;
        private Integer pageNumber;
        private Integer countPerPage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Reservation {
        private String dong;
        private String ho;
        private String carNo;
        private String startDt;
        private String endDt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DelReservation {
        private String dong;
        private String ho;
        private Integer regNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Inout {
        private String dong;
        private String ho;
        private String carType;
        private Integer pageNumber;
        private Integer countPerPage;
    }
}
