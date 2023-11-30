package kr.co.daontec.exception;

import kr.co.daontec.dto.AptiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExController {

    @ExceptionHandler
    public ResponseEntity<?> ex401(AptiException e){
        return AptiResponse.pagingErrRes(401,e.getResultCd(),e.getResultMsg(),null);
    }

    @ExceptionHandler
    public ResponseEntity<?> ex400(Exception e){
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
