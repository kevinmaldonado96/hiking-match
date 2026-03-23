package org.example.hikingmatchservice.repository;

import org.example.hikingmatchservice.entities.PersonalInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IPersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
    Optional<PersonalInformation> findPersonalInformationByEmail(String email);
}
