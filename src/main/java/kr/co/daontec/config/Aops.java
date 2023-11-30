package kr.co.daontec.config;

import com.google.common.hash.Hashing;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.daontec.exception.AptiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class Aops {

    private final ConfigData data;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void validPostHeaders(){}

    @Around("validPostHeaders()")
    public Object checkPostToken(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        String requestURI = request.getRequestURI();
        Object[] ob = joinPoint.getArgs();
        Object result;

        Map<String,String> map = (Map<String, String>) ob[0];

        if (map.get("api-key")==null || map.get("api-key").equals("")){
            throw new AptiException("401","API키가 없습니다.");
        }
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            log.error("[{}] 요청URL : {} 예외발생", ipAddress, requestURI);
            throw e; // 예외를 다시 던져서 상위 핸들러에서 처리할 수 있도록 함
        }
        long endTime = System.currentTimeMillis();
        if (!(requestURI.equals("/external/ping"))&&!(requestURI.equals("/error"))){
            log.info("[{}] 요청URL : {} 응답시간 {} ms", ipAddress, requestURI, endTime - startTime);
        }
        checkToken(map.get("api-key"));

        return result;
    }

    private void checkToken(String token){
        if (!getEncodingSha256(data.getClientId()).equals(token)){
            throw new AptiException("401","잘못된 토큰");
        }
    }

    private String getEncodingSha256(String plainText) {
        return Hashing.sha256().hashString(plainText, StandardCharsets.UTF_8).toString();
    }
}
