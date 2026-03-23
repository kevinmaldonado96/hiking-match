package org.example.hikingroutesservice.dto;

import lombok.Data;
import org.example.hikingroutesservice.models.enums.Difficulty;

import java.util.List;

@Data
public class FilterDto {
    private String name;
    private Difficulty difficulty;
    private List<String> tags;
    private List<Long> temperatureRange;
}
