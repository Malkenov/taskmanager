package com.taskmanager.taskmanager.mapper;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user;
    }

    public static UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(userResponseDto.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        if (user.getTaskList() != null) {
            userResponseDto.setTaskIds(
                    user.getTaskList().stream()
                            .map(Task::getId)
                            .toList()
            );
        }
        return userResponseDto;
    }
}
