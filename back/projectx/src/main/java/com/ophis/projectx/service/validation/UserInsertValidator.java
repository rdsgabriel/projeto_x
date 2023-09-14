package com.ophis.projectx.service.validation;

import com.ophis.projectx.dto.UserInsertDTO;
import com.ophis.projectx.entities.User;
import com.ophis.projectx.repository.UserRepository;
import com.ophis.projectx.resource.exceptions.FieldMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(userInsertDTO.getEmail());
        User name = repository.findByName(userInsertDTO.getName());
        UserDetails login = repository.findByLogin(userInsertDTO.getLogin());

        if (user != null) {
            list.add(new FieldMessage("email", "Email exist"));
        }
        if(name != null) {
            list.add(new FieldMessage("name", "Name exist"));
        }
        if (login != null) {
            list.add(new FieldMessage("login", "Login invalid or existent"));

        }
        for (FieldMessage e : list) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
