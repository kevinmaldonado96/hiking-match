package org.example.services.factory.interfaces;

import org.example.dto.EmailMessageDto;
import org.example.entities.EmailTemplate;
import org.example.entities.enums.EmailCode;

public interface IEmailTemplateBuilder {

    String buildEmailBody(EmailMessageDto emailMessageDto, String html);

    String buildEmailSubject(EmailMessageDto emailMessageDto, String subject);

}
