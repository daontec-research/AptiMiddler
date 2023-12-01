package kr.co.daontec.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.http.ResponseEntity;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseResponse {
    private String resultCd;
    private String resultMsg;

    public static ResponseEntity<?> baseRes(AptiResponse result) {
        return ResponseEntity.status(200).body(
                BaseResponse.builder()
                        .resultCd(result.getResultCd())
                        .resultMsg(result.getResultMsg())
                        .build()
        );
    }

    public static ResponseEntity<?> ErrRes(
            int status,
            String resultCd,
            String resultMsg
    ) {
        return ResponseEntity.status(status).body(
                BaseResponse.builder()
                        .resultCd(resultCd)
                        .resultMsg(resultMsg)
                        .build()
        );
    }
}
