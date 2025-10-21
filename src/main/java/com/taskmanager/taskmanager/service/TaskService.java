package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.EnumStatus;
import com.taskmanager.taskmanager.exception.IncorrectDataException;
import com.taskmanager.taskmanager.mapper.TaskMapper;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    @Transactional
    public TaskResponseDto create(TaskRequestDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IncorrectDataException("Указанный вами пользователь не найден!"));

        Task task = TaskMapper.toEntity(dto, user);
        Task taskSave = taskRepository.save(task);
        return TaskMapper.toDto(taskSave);
    }


    public List<TaskResponseDto> getAll(String status) {
        List<Task> tasks;
        if (status != null) {
            EnumStatus enumStatus = EnumStatus.valueOf(status.toUpperCase());
            tasks = taskRepository.findByStatus(enumStatus);
        } else {
            tasks = taskRepository.findAll();
        }

        return tasks.stream()
                .map(TaskMapper::toDto)
                .toList();
    }

    public TaskResponseDto getById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IncorrectDataException("По вашему запросу, задачи под номером " + id + "не существует!"));

        return TaskMapper.toDto(task);
    }

    @Transactional
    public TaskResponseDto update(Long id, TaskRequestDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("По вашему запросу, задачи под номером " + id + "не существует!"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Указанный вами пользователь не найден!"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDeadline(dto.getDeadline());
        task.setPriority(dto.getPriority());
        task.setAssignee(user);

        taskRepository.save(task);
        return TaskMapper.toDto(task);
    }

    @Transactional
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto ){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("По вашему запросу, задачи под номером " + id + "не существует!"));

        if(dto.getTitle() != null){
            task.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            task.setDescription(dto.getDescription());
        }

        if(dto.getDeadline() != null){
            task.setDeadline(dto.getDeadline());
        }

        if(dto.getPriority() != null){
            task.setPriority(dto.getPriority());
        }

        if(dto.getUserId() != null){
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IncorrectDataException("Указанный вами пользователь не найден!"));
            task.setAssignee(user);
        }


        Task taskSave = taskRepository.save(task);
        return TaskMapper.toDto(taskSave);
    }

    @Transactional
    public void remove(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("По вашему запросу, задачи под номером " + id + "не существует!");
        }
        taskRepository.deleteById(id);
    }

}
