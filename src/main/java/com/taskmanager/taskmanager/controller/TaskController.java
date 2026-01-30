package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.TaskRequestDto;
import com.taskmanager.taskmanager.dto.TaskResponseDto;
import com.taskmanager.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@Valid @RequestBody TaskRequestDto dto) {
        TaskResponseDto created = taskService.create(dto);
        return ResponseEntity.created(URI.create("/tasks/" + (created.getId() != null ? created.getId() : ""))).body(created);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAll() {
        return ResponseEntity.ok(taskService.getAll());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskResponseDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.remove(id);
        return ResponseEntity.noContent().build();
    }
}
