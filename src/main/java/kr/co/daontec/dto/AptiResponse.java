package kr.co.daontec.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.ResponseEntity;




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
}
