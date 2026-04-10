package org.example.services.criteria.impl;

import org.example.dto.FilterDto;
import org.example.services.criteria.RouteCriteria;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public class DifficultyCriteria implements RouteCriteria {

    @Override
    public Optional<Criteria> build(FilterDto filterDto) {
        if (filterDto.getDifficulty() == null) { return Optional.empty(); }
        return Optional.of(Criteria.where("difficulty").is(filterDto.getDifficulty()));
    }
}
