package org.example.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.repository.IUserRepository;
import org.example.validators.annotations.UniqueUser;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, String> {

    private final IUserRepository userRepository;

    public UniqueUserValidator(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String user, ConstraintValidatorContext context) {
        if(user == null || user.isEmpty()) return true;
        return userRepository.findUserByUsername(user).isEmpty();
    }
}
