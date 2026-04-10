package org.example.services.factory;

import org.example.dto.EmailMessageDto;
import org.example.entities.enums.EmailCode;
import org.example.services.factory.impl.EmailConfirmUserRegistrationBuilder;
import org.example.services.factory.impl.EmailNewRouteBuilder;
import org.example.services.factory.interfaces.IEmailTemplateBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EmailTemplateFactory {

    private final Map<EmailCode, IEmailTemplateBuilder> templateBuilderMap = new HashMap<>();

    public EmailTemplateFactory() {
        templateBuilderMap.put(EmailCode.NEW_ROUTE_NOTIFICATION, new EmailNewRouteBuilder());
        templateBuilderMap.put(EmailCode.CONFIRMATION_USER_ROUTE, new EmailConfirmUserRegistrationBuilder());
    }

    public String buildEmailBody(EmailCode emailCode, EmailMessageDto emailMessageDto, String htmlBody) {
        IEmailTemplateBuilder templateBuilder = getTemplateBuilder(emailCode);
        return templateBuilder.buildEmailBody(emailMessageDto, htmlBody);
    }

    public String buildEmailSubject(EmailCode emailCode, EmailMessageDto emailMessageDto, String htmlBody) {
        IEmailTemplateBuilder templateBuilder = getTemplateBuilder(emailCode);
        return templateBuilder.buildEmailSubject(emailMessageDto, htmlBody);
    }

    public IEmailTemplateBuilder getTemplateBuilder(EmailCode emailCode) {
        return templateBuilderMap.get(emailCode);
    }
}
