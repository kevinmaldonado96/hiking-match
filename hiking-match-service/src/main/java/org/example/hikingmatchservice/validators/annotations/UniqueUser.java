package org.example.hikingmatchservice.validators.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.hikingmatchservice.validators.MatchesPasswordValidator;
import org.example.hikingmatchservice.validators.UniqueUserValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueUserValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {

    String message() default "User already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
