package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.UserRole;
import com.taskmanager.taskmanager.exception.IncorrectDataException;
import com.taskmanager.taskmanager.mapper.UserMapper;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto createUser(UserRequestDto dto) {
        User user = userMapper.toEntity(dto);
        User userSave = userRepository.save(user);
        return userMapper.toDto(userSave);
    }

    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IncorrectDataException("Пользователь с id" + id + "не найден!"));
        return userMapper.toDto(user);
    }

    public List<UserResponseDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto user) {
        User userId = userRepository.findById(id).orElseThrow(() -> new IncorrectDataException("Пользователь с id" + id + "не найден!"));

        userId.setEmail(user.getEmail());
        userId.setFirstName(user.getFirstName());
        userId.setLastName(user.getLastName());
        userId.setPassword(user.getPassword());
        return userMapper.toDto(userId);
    }

    @Transactional
    // -- Получение роли админа --
    public void userNewRole(Long id, UserRole role){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IncorrectDataException("Пользователь с id" + id + "не найден!"));
        user.setUserRole(role);
        userRepository.save(user);
    }


    @Transactional
    public void removeUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IncorrectDataException("Пользователь с id" + id + "не найден!");
        }
        userRepository.deleteById(id);
    }



}

