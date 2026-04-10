package org.example.repository;

import org.example.models.LogHttp;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHttpRespository extends ReactiveMongoRepository<LogHttp, String> {
}
