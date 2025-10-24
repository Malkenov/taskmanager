package com.taskmanager.taskmanager.mapper;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.UserRole;
import com.taskmanager.taskmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUserMapper {
    private UserMapper userMapper;

    @Test
    void testToEntity(){
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail("user@mail.ru");
        dto.setFirstName("user");
        dto.setLastName("user");
        dto.setPassword("password");

        User user = userMapper.toEntity(dto);

        assertEquals("user@mail.ru",dto.getEmail());
        assertEquals("user",dto.getFirstName());
        assertEquals("user",dto.getLastName());
        assertEquals("password",dto.getPassword());
    }

    @Test
    void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@mail.ru");
        user.setFirstName("user");
        user.setLastName("user");
        user.setPassword("password");
        user.setUserRole(UserRole.USER);


        UserResponseDto dto = userMapper.toDto(user);

        assertEquals("user@mail.ru",dto.getEmail());
        assertEquals("user",dto.getFirstName());
        assertEquals("user",dto.getLastName());
        assertEquals(UserRole.USER,dto.getUserRole());
    }
}
