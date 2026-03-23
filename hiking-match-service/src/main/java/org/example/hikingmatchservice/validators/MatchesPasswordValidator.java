package org.example.hikingmatchservice.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.hikingmatchservice.dto.UserDto;
import org.example.hikingmatchservice.validators.annotations.MatchesPassword;

public class MatchesPasswordValidator implements ConstraintValidator<MatchesPassword, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        return (userDto.getPassword() != null && userDto.getPassword().equals(userDto.getConfirmPassword()));
    }
}
