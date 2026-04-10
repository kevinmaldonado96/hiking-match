package org.example.services.criteria.impl;

import org.example.dto.FilterDto;
import org.example.services.criteria.RouteCriteria;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public class TagsCriteria implements RouteCriteria {
    @Override
    public Optional<Criteria> build(FilterDto filterDto) {
        if (filterDto.getTags() == null) { return Optional.empty(); }
        return Optional.of(Criteria.where("tags").in(filterDto.getTags()));
    }
}
