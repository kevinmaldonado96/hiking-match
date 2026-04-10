package org.example.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "log_http")
public class LogHttp {

    @Id
    private String id;

    @NotNull
    private String ip;

    @NotNull
    private String log;

    @NotNull
    private String host;

    @NotNull
    private String method;

    @Builder
    public LogHttp(String ip, String log, String host, String method) {
        this.ip = ip;
        this.log = log;
        this.host = host;
        this.method = method;
    }
}
