package kr.co.daontec.service;

import jakarta.annotation.PostConstruct;
import kr.co.daontec.config.ConfigData;
import kr.co.daontec.service.davans.DavansClient;
import kr.co.daontec.service.davans.DavansHostConnect;
import kr.co.daontec.webclient.RequestApti;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DavansInit implements ApplicationRunner {
    private final RequestApti requestApti;
    private final ConfigData configData;
    private DavansClient davansClient;


    @Override
    public void run(ApplicationArguments args) {
        Thread tcpClientThread = new Thread(davansClient);
        tcpClientThread.start();
    }

    public void reRun() {
        log.info("재접속시도중..");
        Thread tcpClientThread = new Thread(davansClient);
        tcpClientThread.start();
    }

    @PostConstruct
    public void init() {
        davansClient = new DavansClient(
                this,
                requestApti,
                configData
        );
    }
}
