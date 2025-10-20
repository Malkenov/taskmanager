package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.UserRole;
import com.taskmanager.taskmanager.mapper.UserMapper;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        User userSave = userRepository.save(user);
        return userMapper.toDto(userSave);
    }

    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Нету такого пользователя!"));
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        User userId = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Нету такого пользователя!"));

        userId.setEmail(user.getEmail());
        userId.setFirstName(user.getFirstName());
        userId.setLastName(user.getLastName());
        userId.setPassword(user.getPassword());
        return userMapper.toDto(userId);
    }
    // -- Получение роли админа --
    public void userNewRole(Long id, UserRole role){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Нету такого пользователя!"));
        user.setUserRole(role);
        userRepository.save(user);
    }


    public void removeUser(Long id) {
        if (userRepository.existsById(id)) {
            throw new RuntimeException("Нету такого пользователя!");
        }
        userRepository.deleteById(id);
    }



}

