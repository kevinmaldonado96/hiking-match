package org.example.hikingroutesservice.services.impl;

import lombok.extern.java.Log;
import org.example.hikingroutesservice.models.LogHttp;
import org.example.hikingroutesservice.repository.LogHttpRespository;
import org.example.hikingroutesservice.services.ILogHttpService;
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
