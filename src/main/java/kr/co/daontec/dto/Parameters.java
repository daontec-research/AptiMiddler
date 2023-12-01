package kr.co.daontec.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class Parameters {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RegisterCar {
        @NotEmpty(message = "동이 없습니다.")
        private String dong;
        @NotEmpty(message = "호가 없습니다.")
        private String ho;
        @NotNull(message = "페이지넘버가 없습니다.")
        private Integer pageNumber;
        @NotNull(message = "페이지당개수가 없습니다.")
        private Integer countPerPage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ReservationList {
        @NotEmpty(message = "동이 없습니다.")
        private String dong;
        @NotEmpty(message = "호가 없습니다.")
        private String ho;
        @NotEmpty(message = "조회시작일이 없습니다.")
        private String startDt;
        @NotEmpty(message = "조회종료일이 없습니다.")
        private String endDt;
        @NotNull(message = "페이지넘버가 없습니다.")
        private Integer pageNumber;
        @NotNull(message = "페이지당개수가 없습니다.")
        private Integer countPerPage;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Reservation {
        @NotEmpty(message = "동이 없습니다.")
        private String dong;
        @NotEmpty(message = "호가 없습니다.")
        private String ho;
        @NotEmpty(message = "차량번호가 없습니다.")
        private String carNo;
        @NotEmpty(message = "예약시작일이 없습니다.")
        private String startDt;
        @NotEmpty(message = "예약종료일이 없습니다.")
        private String endDt;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DelReservation {
        @NotEmpty(message = "동이 없습니다.")
        private String dong;
        @NotEmpty(message = "호가 없습니다.")
        private String ho;
        @NotNull(message = "등록번호가 없습니다.")
        private Integer regNumber;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Inout {
        @NotEmpty(message = "동이 없습니다.")
        private String dong;
        @NotEmpty(message = "호가 없습니다.")
        private String ho;
        @NotEmpty(message = "carType이 없습니다")
        private String carType;
        @NotNull(message = "페이지넘버가 없습니다.")
        private Integer pageNumber;
        @NotNull(message = "페이지당개수가 없습니다.")
        private Integer countPerPage;
    }
}
