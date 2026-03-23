package org.example.hikingmatchservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.hikingmatchservice.entities.Injury;
import org.example.hikingmatchservice.entities.enums.InjurySeverity;
import org.example.hikingmatchservice.entities.enums.InjuryType;

@Data
public class InjuryDto {

    @NotNull(message = "The injury description is required")
    private String description;

    @NotNull(message = "The injury type is required")
    private InjuryType type;

    @NotNull(message = "The injury severity is required")
    private InjurySeverity severity;

    public InjuryDto(String description, InjuryType type, InjurySeverity severity) {
        this.description = description;
        this.type = type;
        this.severity = severity;
    }

    public static InjuryDto fromInjury(Injury injury) {
        return new InjuryDto(injury.getDescription(), injury.getType(), injury.getSeverity());
    }
}
