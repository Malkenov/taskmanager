package com.taskmanager.taskmanager.dto;

import jakarta.validation.Validation;
import jakarta.validation.*;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validUserRequestDto(){
        UserRequestDto dto = UserRequestDto.builder()
                .email("user@mail.ru")
                .firstName("name")
                .lastName("lastName")
                .password("password")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(),"Валидация работает");
    }


    @Test
    void invalidUserRequestDto_NotBlank(){
        UserRequestDto dto = UserRequestDto.builder()
                .email("user@mail.ru")
                .firstName("")
                .lastName("")
                .password("password")
                .build();

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty(),"Валидация не прошла");
    }
}
