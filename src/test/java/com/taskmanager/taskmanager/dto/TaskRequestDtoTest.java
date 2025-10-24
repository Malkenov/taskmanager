package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.enums.EnumPriority;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validTaskRequestDto(){
        TaskRequestDto dto = TaskRequestDto.builder()
                .title("Заголовок")
                .description("Описание")
                .deadline(LocalDate.now().plusDays(1))
                .priority(EnumPriority.LOW)
                .userId(1L)
                .build();

        Set<ConstraintViolation<TaskRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty(),"Валидация прошла успешно");
    }

    @Test
    void invalidTaskRequest_NotBlank(){
        TaskRequestDto dto = TaskRequestDto.builder()
                .title("")
                .description("Описание")
                .deadline(LocalDate.now().plusDays(1))
                .priority(EnumPriority.LOW)
                .userId(1L)
                .build();

    Set<ConstraintViolation<TaskRequestDto>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(),"Ошибка валидации");
    assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }


    @Test
    void invalidTaskRequest_NotNull(){
        TaskRequestDto dto = TaskRequestDto.builder()
                .title("Заголовок")
                .description("Описание")
                .deadline(LocalDate.now().plusDays(1))
                .priority(EnumPriority.LOW)
                .userId(null)
                .build();
    Set<ConstraintViolation<TaskRequestDto>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty(),"Ошибка валидации");

    }
}
