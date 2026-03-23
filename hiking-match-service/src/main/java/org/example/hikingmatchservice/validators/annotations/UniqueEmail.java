package org.example.hikingmatchservice.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.hikingmatchservice.validators.MatchesPasswordValidator;
import org.example.hikingmatchservice.validators.UniqueEmailValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
