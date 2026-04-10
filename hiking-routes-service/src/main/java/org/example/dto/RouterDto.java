package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.enums.ActionEnum;
import org.example.models.enums.Difficulty;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class RouterDto implements Serializable {

    private String name;
    private String description;
    private Difficulty difficulty;
    private List<String> tags;
    private List<Long> temperatureRange;
    private Integer limit;
    private ActionEnum action;

    @Builder
    public RouterDto(String name, String description, Difficulty difficulty, List<String> tags, List<Long> temperatureRange, Integer limit, ActionEnum action) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.tags = tags;
        this.temperatureRange = temperatureRange;
        this.limit = limit;
        this.action = action;
    }
}
