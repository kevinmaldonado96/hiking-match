package org.example.hikingroutesservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull
    private Long id;
    @NotNull
    private Boolean isInjury;
    @NotNull
    private List<InjuryDto> injuries;


}
