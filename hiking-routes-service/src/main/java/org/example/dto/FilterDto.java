package org.example.dto;

import lombok.Data;
import org.example.models.enums.Difficulty;

import java.util.List;

@Data
public class FilterDto {
    private String name;
    private Difficulty difficulty;
    private List<String> tags;
    private List<Long> temperatureRange;
}
