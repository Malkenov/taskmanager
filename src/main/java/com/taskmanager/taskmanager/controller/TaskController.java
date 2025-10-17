package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.repository.TaskRepository;
import com.taskmanager.taskmanager.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }


}
