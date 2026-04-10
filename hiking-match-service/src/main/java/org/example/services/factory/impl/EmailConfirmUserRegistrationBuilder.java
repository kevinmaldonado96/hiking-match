package org.example.services.factory.impl;

import org.example.dto.EmailMessageDto;
import org.example.dto.RouteDto;
import org.example.dto.UserDto;
import org.example.entities.enums.EmailCode;
import org.example.services.factory.interfaces.IEmailTemplateBuilder;

public class EmailConfirmUserRegistrationBuilder implements IEmailTemplateBuilder {
    @Override
    public String buildEmailBody(EmailMessageDto emailMessageDto, String html) {
        RouteDto routeDto = emailMessageDto.getRouteDto();
        UserDto userDto = emailMessageDto.getUserDto();

        return html
                .replace("{{user_name}}", userDto.getName())
                .replace("{{route_name}}", routeDto.getName())
                .replace("{{route_description}}", routeDto.getDescription())
                .replace("{{difficulty}}", routeDto.getDifficulty().toString())
                .replace("{{temperature_min}}", String.valueOf(routeDto.getTemperatureRange().get(0)))
                .replace("{{temperature_max}}", String.valueOf(routeDto.getTemperatureRange().get(1)))
                .replace("{{tags}}", String.join(", ", routeDto.getTags()));
    }

    @Override
    public String buildEmailSubject(EmailMessageDto emailMessageDto, String subject) {
        RouteDto routeDto = emailMessageDto.getRouteDto();
        UserDto userDto = emailMessageDto.getUserDto();

        return subject
                .replace("{{route_name}}", routeDto.getName())
                .replace("{{username}}", userDto.getName());
    }

}
