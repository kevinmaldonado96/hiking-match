package org.example.dto;


import lombok.Data;
import org.example.dto.enums.InjurySeverity;

@Data
public class InjuryDto {
    private InjurySeverity severity;
}
