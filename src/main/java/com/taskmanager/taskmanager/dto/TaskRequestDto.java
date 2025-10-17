package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.enums.EnumPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class TaskRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private LocalDate deadline;

    /* @Pattern(regexp = "PROCESS|COMPLETE|CANCELLED", message = "Некорректный статус") - такое в поле с enum не передается
    private EnumStatus status; */

    @NotNull(message = "Укажите точную продолжительность - LOW,MEDIUM или HIGH")
    private EnumPriority priority;

    @NotNull(message = "Id пользователя обязательно")
    private Long userId;
}
