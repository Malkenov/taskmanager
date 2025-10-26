package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.enums.EnumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByStatus(EnumStatus status);
}
