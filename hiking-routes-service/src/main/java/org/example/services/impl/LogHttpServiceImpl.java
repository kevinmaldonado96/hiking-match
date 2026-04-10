package org.example.services.impl;

import org.example.models.LogHttp;
import org.example.repository.LogHttpRespository;
import org.example.services.ILogHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogHttpServiceImpl implements ILogHttpService {

    @Autowired
    private LogHttpRespository logHttpRespository;

    @Override
    public void saveLogHttp(String serverHost, String path, String method, String ip, String log) {
        LogHttp logHttp = LogHttp
                .builder()
                .log(log)
                .ip(ip)
                .method(method)
                .host(serverHost)
                .build();

        logHttpRespository.save(logHttp).subscribe();
    }
}
