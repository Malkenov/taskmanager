package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.EnumStatus;
import com.taskmanager.taskmanager.exception.NotFoundException;
import com.taskmanager.taskmanager.mapper.TaskMapper;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskResponseDto> getAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskResponseDto create(TaskRequestDto dto) {
        User assignee = null;
        if (dto.getUserId() != null) {
            assignee = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Assignee not found: " + dto.getUserId()));
        }
        Task task = TaskMapper.toEntity(dto, assignee);
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }

    @Transactional
    public void remove(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new NotFoundException("Task with id " + id + " not found!");
        }
        taskRepository.deleteById(id);
    }

    @Transactional
    public TaskResponseDto updateStatus(Long id, String status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found: " + id));
        try {
            EnumStatus newStatus = EnumStatus.valueOf(status);
            task.setStatus(newStatus);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        Task saved = taskRepository.save(task);
        return TaskMapper.toDto(saved);
    }
}
