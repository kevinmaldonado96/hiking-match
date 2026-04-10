package org.example.repository;

import org.example.entities.EmailTemplate;
import org.example.entities.enums.EmailCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
    Optional<EmailTemplate> findByCode(EmailCode code);
}
