package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }


    @PostMapping
    public ResponseEntity<TaskResponseDto> createTask(@RequestBody @Valid TaskRequestDto dto){
        TaskResponseDto taskResponseDto = taskService.create(dto);
        return ResponseEntity.ok(taskResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getByIdTasks(@PathVariable Long id){
        TaskResponseDto taskResponseDto = taskService.getById(id);
        return ResponseEntity.ok(taskResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAllTasks(
            @RequestParam(required = false) String status){
        List<TaskResponseDto> taskResponseDto = taskService.getAll(status);
        return ResponseEntity.ok(taskResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> updateTask(@PathVariable Long id,
                                                      @RequestBody TaskRequestDto dto){
        TaskResponseDto taskResponseDto = taskService.update(id,dto);
        return ResponseEntity.ok(taskResponseDto);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> removeTask(@PathVariable Long id){
        taskService.remove(id);
        return ResponseEntity.noContent().build();
    }

}
