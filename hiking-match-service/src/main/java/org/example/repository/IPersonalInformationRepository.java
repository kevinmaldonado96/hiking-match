package org.example.repository;

import org.example.entities.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
    Optional<PersonalInformation> findPersonalInformationByEmail(String email);
}
