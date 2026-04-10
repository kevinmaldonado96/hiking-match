package org.example.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entities.enums.IdentityType;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "personal_information")
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private IdentityType identityType;
    private String identityNumber;
    private String address;
    private String email;
    private String phoneNumber;
    private Boolean anyInjury;

    @OneToMany(mappedBy = "personalInformation", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Injury> injuries;

    @Builder
    public PersonalInformation(String name, String lastname, IdentityType identityType, String identityNumber, String address, String email, String phoneNumber, Boolean anyInjury, List<Injury> injuries) {
        this.name = name;
        this.lastname = lastname;
        this.identityType = identityType;
        this.identityNumber = identityNumber;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.anyInjury = anyInjury;
        this.injuries = injuries;
    }
}
