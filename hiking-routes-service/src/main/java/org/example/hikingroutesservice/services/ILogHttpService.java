package org.example.hikingroutesservice.services;

public interface ILogHttpService {
    void saveLogHttp(String serverHost, String path, String method, String ip, String log);
}
