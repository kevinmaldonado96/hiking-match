package org.example.hikingroutesservice.dto;


import lombok.Data;
import org.example.hikingroutesservice.dto.enums.InjurySeverity;

@Data
public class InjuryDto {
    private InjurySeverity severity;
}
