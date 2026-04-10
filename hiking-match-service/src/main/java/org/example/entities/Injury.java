package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.InjuryDto;
import org.example.entities.enums.InjurySeverity;
import org.example.entities.enums.InjuryType;

@Data
@Entity
@NoArgsConstructor
@Table(name = "injuries")
public class Injury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private InjuryType type;
    private InjurySeverity severity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private PersonalInformation personalInformation;

    @Builder
    public Injury(String description, InjuryType type, InjurySeverity severity) {
        this.description = description;
        this.type = type;
        this.severity = severity;
    }

    public static Injury fromDto(InjuryDto dto){
        return Injury
                .builder()
                .description(dto.getDescription())
                .type(dto.getType())
                .severity(dto.getSeverity())
                .build();
    }
}
