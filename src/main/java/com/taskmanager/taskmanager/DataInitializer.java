package com.taskmanager.taskmanager;

import com.taskmanager.taskmanager.entity.User;
import com.taskmanager.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if(userRepository.findByEmail("admin").isEmpty()){
            User admin = new User();
            admin.setEmail("admin");
            admin.setFirstName("Asanali");
            admin.setLastName("Malkenov");
            admin.setPassword("admin");
            userRepository.save(admin);
            System.out.println("Создан админ");
        }
    }
}
