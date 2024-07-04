package com.example.sales_manager.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    @Override
    public void initialize(CheckEmail constraintAnnotation) {
        // Any initialization goes here if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return false; // null or empty values are considered invalid
        }
        return Pattern.matches(EMAIL_PATTERN, email);
    }
}
