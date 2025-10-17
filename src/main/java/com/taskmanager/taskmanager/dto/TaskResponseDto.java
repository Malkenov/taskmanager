package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.enums.EnumPriority;
import com.taskmanager.taskmanager.enums.EnumStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDto {

    private String title;
    private String description;
    private LocalDate deadline;
    private EnumStatus status;
    private EnumPriority priority;
    private Long userId;

}
