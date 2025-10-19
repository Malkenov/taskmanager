package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.mapper.UserMapper;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponseDto createUser(User user) {
        User user1 = userRepository.save(user);
        return UserMapper.toDto(user1);
    }

    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Нету такого пользователя!"));
        return UserMapper.toDto(user);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).toList();
    }

    public UserResponseDto updateUser(Long id, User user) {
        User userId = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Нету такого пользователя!"));

        userId.setEmail(user.getEmail());
        userId.setFirstName(user.getFirstName());
        userId.setLastName(user.getLastName());
        userId.setPassword(user.getPassword());
        return UserMapper.toDto(userId);
    }

    public void removeUser(Long id) {
        if (userRepository.existsById(id)) {
            throw new RuntimeException("Нету такого пользователя!");
        }
        userRepository.deleteById(id);
    }
}

