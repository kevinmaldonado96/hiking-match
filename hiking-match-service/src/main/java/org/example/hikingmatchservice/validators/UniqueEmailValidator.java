package org.example.hikingmatchservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.hikingmatchservice.repository.IPersonalInformationRepository;
import org.example.hikingmatchservice.validators.annotations.UniqueEmail;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final IPersonalInformationRepository personalInformationRepository;

    public UniqueEmailValidator(IPersonalInformationRepository personalInformationRepository) {
        this.personalInformationRepository = personalInformationRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(email == null || email.isEmpty()) return true;
        return personalInformationRepository.findPersonalInformationByEmail(email).isEmpty();
    }
}
