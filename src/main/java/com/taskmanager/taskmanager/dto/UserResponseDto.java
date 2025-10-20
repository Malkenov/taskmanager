package com.taskmanager.taskmanager.dto;

import com.taskmanager.taskmanager.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
    private List<Long> taskIds;
}
