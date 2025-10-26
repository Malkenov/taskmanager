package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.EnumPriority;
import com.taskmanager.taskmanager.mapper.TaskMapper;
import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private TaskRequestDto dto;
    private Task task;
    private User user;


    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        dto = TaskRequestDto.builder()
                .title("Test")
                .description("Test description")
                .deadline(LocalDate.now().plusDays(1))
                .priority(EnumPriority.LOW)
                .userId(1L)
                .build();

        user = new User();
        user.setId(1L);

        task = TaskMapper.toEntity(dto,user);
    }

    @Test
    void createTask(){

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDto result = taskService.create(dto);

        assertEquals("Test",result.getTitle());
        assertEquals(EnumPriority.LOW,result.getPriority());
        verify(userRepository).findById(1L);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testUserNotFound(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, ()-> taskService.create(dto));

        assertEquals("Указанный вами пользователь не найден!",ex.getMessage());
        verify(taskRepository,never()).save(any());
    }
}
