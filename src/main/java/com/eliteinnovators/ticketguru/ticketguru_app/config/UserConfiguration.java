package com.eliteinnovators.ticketguru.ticketguru_app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.eliteinnovators.ticketguru.ticketguru_app.domain.User;
import com.eliteinnovators.ticketguru.ticketguru_app.repository.UserRepository;

@Configuration
public class UserConfiguration {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserConfiguration(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initData() {
    return args -> {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("admin"));
            admin.setRole("ADMIN"); 
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPasswordHash(passwordEncoder.encode("user"));
            user.setRole("USER"); 
            userRepository.save(user);
        }
    };
    }

}
