package com.taskmanager.taskmanager.dto;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @Email(message = "Введите e-mail!")
    private String email;

    @NotBlank(message = "Имя обязательно!")
    private String firstName;

    @NotBlank(message = "Введите фамилию")
    private String lastName;

    @NotBlank(message = "пароль обязательно!")
    @Size(min = 3, max = 20, message = "Пароль должен быть минимум 3 символа и не более 20 символов!")
    private String password;
}
