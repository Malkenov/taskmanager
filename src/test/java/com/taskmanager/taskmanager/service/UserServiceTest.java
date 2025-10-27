package com.taskmanager.taskmanager.service;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.mapper.UserMapper;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private User user;
    private UserService userService;
    private UserRequestDto dto;
    private UserMapper userMapper;

    @Test
    void setUp(){
        MockitoAnnotations.openMocks(this);

        dto = UserRequestDto.builder()
                .email("user@mail.ru")
                .firstName("user")
                .lastName("user")
                .password("password")
                .build();

        user = userMapper.toEntity(dto);
    }

    @Test
    void createUser(){
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
    }
}
