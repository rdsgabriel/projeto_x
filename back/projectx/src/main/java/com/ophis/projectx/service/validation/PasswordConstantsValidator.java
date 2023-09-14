package com.ophis.projectx.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;
import java.util.List;

public class PasswordConstantsValidator implements ConstraintValidator<PasswordValid, String> {


    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {


        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                new IllegalSequenceRule(EnglishSequenceData.USQwerty)
        ));

        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()){
            return true;
        }

        List<String> messages = validator.getMessages(result);
        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
