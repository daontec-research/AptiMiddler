package kr.co.daontec.dto.aptiresponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarInOutRes {
    private String resultCode;
    private String message;
    private String fileName;
    private Long carInOutHistoryId;
}
