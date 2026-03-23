package org.example.hikingroutesservice.repository;

import org.example.hikingroutesservice.models.LogHttp;
import org.example.hikingroutesservice.models.Route;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHttpRespository extends ReactiveMongoRepository<LogHttp, String> {
}
