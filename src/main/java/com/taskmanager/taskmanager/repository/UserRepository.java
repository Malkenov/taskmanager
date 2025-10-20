package com.taskmanager.taskmanager.repository;

import com.taskmanager.taskmanager.entity.Task;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.EnumStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     Optional<User> findByEmail(String email);
}
