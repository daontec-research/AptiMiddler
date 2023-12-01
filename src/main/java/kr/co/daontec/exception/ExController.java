package kr.co.daontec.exception;

import kr.co.daontec.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExController {

    @ExceptionHandler
    public ResponseEntity<?> ex401(AptiException e) {
        return BaseResponse.ErrRes(401, e.getResultCd(), e.getResultMsg());
    }

    @ExceptionHandler
    public ResponseEntity<?> ex400(Exception e) {
        return BaseResponse.ErrRes(400, "400", e.getMessage());
    }

    @ExceptionHandler()
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex) {
        return BaseResponse.ErrRes(400, "400", ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}
