package org.example.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EmailMessageDto {
    private RouteDto routeDto;
    private UserDto userDto;

    @Builder
    public EmailMessageDto(RouteDto routeDto, UserDto userDto) {
        this.routeDto = routeDto;
        this.userDto = userDto;
    }
}
