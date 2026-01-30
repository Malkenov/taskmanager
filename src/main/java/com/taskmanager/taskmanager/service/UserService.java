package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.UserRole;
import com.taskmanager.taskmanager.exception.IncorrectDataException;
import com.taskmanager.taskmanager.exception.NotFoundException;
import com.taskmanager.taskmanager.exception.BadRequestException;
import com.taskmanager.taskmanager.mapper.UserMapper;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        if (dto.getEmail() == null) {
            throw new BadRequestException("Email is required");
        }
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("User with email already exists");
        }
        User user = userMapper.toEntity(dto);
        // hash password before saving
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if (dto.getUserRole() != null) {
            user.setUserRole(dto.getUserRole());
        }
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Transactional
    public void removeUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " not found!");
        }
        userRepository.deleteById(id);
    }
}
