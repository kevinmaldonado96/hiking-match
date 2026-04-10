package org.example.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.dto.UserDto;
import org.example.validators.annotations.MatchesPassword;

public class MatchesPasswordValidator implements ConstraintValidator<MatchesPassword, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return (userDto.getPassword() != null && userDto.getPassword().equals(userDto.getConfirmPassword()));
    }
}
