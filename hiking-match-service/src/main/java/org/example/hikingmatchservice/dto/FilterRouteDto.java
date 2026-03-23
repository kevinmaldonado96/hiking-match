package org.example.hikingmatchservice.dto;

import lombok.Data;
import org.example.hikingmatchservice.dto.enums.Difficulty;

import java.util.List;

@Data
public class FilterRouteDto {
    private String name;
    private Difficulty difficulty;
    private List<String> tags;
    private List<Long> temperatureRange;
}