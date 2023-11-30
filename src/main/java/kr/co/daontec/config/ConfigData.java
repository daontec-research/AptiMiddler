package kr.co.daontec.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ConfigData {
    @Value("${davans.host}")
    private String host;
    @Value("${davans.port}")
    private int port;
    @Value("${davans.size}")
    private int size;
    @Value("${apti.url}")
    private String aptiUrl;
    @Value("${apti.key}")
    private String aptiKey;
    @Value("${apti.code}")
    private String aptiCode;
    @Value("${apti.clientid}")
    private String clientId;
}
