package org.example.services.criteria.impl;

import org.example.dto.FilterDto;
import org.example.services.criteria.RouteCriteria;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Optional;

public class TemperatureCriteria implements RouteCriteria {
    @Override
    public Optional<Criteria> build(FilterDto filterDto) {
        if (filterDto.getTemperatureRange() == null){ return Optional.empty(); }

        Criteria criteria = new Criteria();

        Long tempRangeMin = filterDto.getTemperatureRange().get(0);
        Long tempRangeMax = filterDto.getTemperatureRange().get(1);

        criteria.andOperator(new Criteria().andOperator(
                Criteria.where("temperatureRange.1").gte(tempRangeMin),
                Criteria.where("temperatureRange.0").lte(tempRangeMax)
        ));

        return Optional.of(criteria);
    }
}
