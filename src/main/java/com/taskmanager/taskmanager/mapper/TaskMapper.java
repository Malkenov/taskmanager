package com.taskmanager.taskmanager.mapper;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public static Task toEntity(TaskRequestDto dto, User user){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());
        task.setAssignee(user);
        return task;
    }

    public static TaskResponseDto toDto(Task task){
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        taskResponseDto.setTitle(task.getTitle());
        taskResponseDto.setDescription(task.getDescription());
        taskResponseDto.setDeadline(task.getDeadline());
        taskResponseDto.setStatus(task.getStatus());
        taskResponseDto.setPriority(task.getPriority());
        taskResponseDto.setUserId(task.getAssignee() != null ? task.getAssignee().getId() : null);
        return taskResponseDto;
    }
}
