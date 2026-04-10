package org.example.services.criteria;

import org.example.dto.FilterDto;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public interface RouteCriteria {
    Optional<Criteria> build(FilterDto filterDto);
}
