package com.taskmanager.taskmanager.mapper;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.EnumPriority;
import com.taskmanager.taskmanager.enums.EnumStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTaskMapper {

    @Test
    void testToEntity(){
        TaskRequestDto dto = new TaskRequestDto();
        dto.setTitle("Заголовок");
        dto.setDescription("Описание задании");
        dto.setDeadline(LocalDate.of(2025,10,22));
        dto.setPriority(EnumPriority.LOW);

        User user = new User();
        user.setId(1L);

        Task task = TaskMapper.toEntity(dto,user);

        assertEquals("Заголовок",task.getTitle());
        assertEquals("Описание задании",task.getDescription());
        assertEquals(LocalDate.of(2025,10,22),task.getDeadline());
        assertEquals(EnumPriority.LOW,task.getPriority());
        assertEquals(user,task.getAssignee());
    }


    @Test
    void testToDto(){
        User user = new User();
        user.setId(10L);

        Task task = new Task();
        task.setTitle("Заголовок");
        task.setDescription("Описание задании");
        task.setDeadline(LocalDate.of(2025,10,22));
        task.setStatus(EnumStatus.PROCESS);
        task.setPriority(EnumPriority.LOW);


        TaskResponseDto dto = TaskMapper.toDto(task);

        assertEquals("Заголовок",dto.getTitle());
        assertEquals("Описание задании",dto.getDescription());
        assertEquals(LocalDate.of(2025,10,22),dto.getDeadline());
        assertEquals(EnumStatus.PROCESS,dto.getStatus());
        assertEquals(EnumPriority.LOW,dto.getPriority());
        assertEquals(10L,dto.getUserId());

    }
}
