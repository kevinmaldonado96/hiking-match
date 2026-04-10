package org.example.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.enums.EmailCode;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "email_templates")
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true, length = 100)
    private EmailCode code;

    @Column(nullable = false, length = 255)
    private String subject;

    @Column(name = "html_content", nullable = false, columnDefinition = "TEXT")
    private String htmlContent;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public EmailTemplate(EmailCode code, String subject, String htmlContent, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.code = code;
        this.subject = subject;
        this.htmlContent = htmlContent;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
