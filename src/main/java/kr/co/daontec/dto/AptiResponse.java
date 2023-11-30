package kr.co.daontec.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;




@Builder
@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AptiResponse {

    private String resultCd;
    private String resultMsg;
    private Integer pageNumber;
    private Integer countPerPage;
    private Boolean isNext;
    private Integer total;
    private Object list;


    public static ResponseEntity<?> pagingRes(
            int pageNumber,
            int countPerPage,
            boolean isNext,
            int total,
            Object list
    ) {
        return ResponseEntity.status(200).body(
                AptiResponse.builder()
                        .resultCd("00")
                        .resultMsg("정상")
                        .pageNumber(pageNumber)
                        .countPerPage(countPerPage)
                        .isNext(isNext)
                        .total(total)
                        .list(list)
                        .build()
        );
    }

    public static ResponseEntity<?> reservationDeleteRes(AptiResponse result){
        return ResponseEntity.status(200).body(
                AptiResponse.builder()
                        .resultCd(result.getResultCd())
                        .resultMsg(result.getResultMsg())
                        .build()
        );
    }

    public static ResponseEntity<?> pagingErrRes(
            int status,
            String resultCd,
            String resultMsg,
            Object list
    ) {
        return ResponseEntity.status(status).body(
                AptiResponse.builder()
                        .resultCd(resultCd)
                        .resultMsg(resultMsg)
                        .list(list)
                        .build()
        );
    }

}
