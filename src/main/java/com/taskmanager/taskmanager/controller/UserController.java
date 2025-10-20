package com.taskmanager.taskmanager.controller;

import com.taskmanager.taskmanager.dto.UserRequestDto;
import com.taskmanager.taskmanager.dto.UserResponseDto;
import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.enums.UserRole;
import com.taskmanager.taskmanager.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.NoArgsConstructor;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto dto){
        UserResponseDto userResponseDto = userService.createUser(dto);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll(){
        List<UserResponseDto> userResponseDto = userService.getAll();
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.getById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @RequestBody UserRequestDto dto){
        UserResponseDto userResponseDto = userService.updateUser(id, dto);
        return ResponseEntity.ok(userResponseDto);
    }


    // -- Получение админа --
    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> registerAdmin(@PathVariable Long id,
                                              @RequestParam UserRole role){
        userService.userNewRole(id,role);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id){
        userService.removeUser(id);
        return ResponseEntity.noContent().build();
    }
}
