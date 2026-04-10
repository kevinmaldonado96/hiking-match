package org.example.services.factory.impl;

import org.example.dto.EmailMessageDto;
import org.example.dto.RouteDto;
import org.example.entities.enums.EmailCode;
import org.example.services.factory.interfaces.IEmailTemplateBuilder;

public class EmailNewRouteBuilder implements IEmailTemplateBuilder {
    @Override
    public String buildEmailSubject(EmailMessageDto emailMessageDto, String subject) {
        RouteDto routeDto = emailMessageDto.getRouteDto();
        return subject.replace("{{name}}", routeDto.getName());
    }

    @Override
    public String buildEmailBody(EmailMessageDto emailMessageDto, String html) {
        RouteDto routeDto = emailMessageDto.getRouteDto();

        return html
                .replace("{{name}}", routeDto.getName())
                .replace("{{description}}", routeDto.getDescription())
                .replace("{{difficulty}}", routeDto.getDifficulty().toString())
                .replace("{{tags}}", String.join(", ", routeDto.getTags()))
                .replace("{{temperature_min}}", String.valueOf(routeDto.getTemperatureRange().get(0)))
                .replace("{{temperature_max}}", String.valueOf(routeDto.getTemperatureRange().get(1)))
                .replace("{{limit}}", String.valueOf(routeDto.getLimit()));

    }


}
