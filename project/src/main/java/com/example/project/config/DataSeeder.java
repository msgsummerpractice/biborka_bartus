package com.example.project.config;

import com.example.project.model.Role;
import com.example.project.model.User;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        Role adminRole = roleRepository.save(new Role(null, "ROLE_ADMIN"));
        Role userRole = roleRepository.save(new Role(null, "ROLE_USER"));

        User admin = new User(null, "Admin", "Admin", "admin", "admin@test.com",
                passwordEncoder.encode("admin123"), Set.of(adminRole));

        User user = new User(null, "Regular", "User", "user", "user@test.com",
                passwordEncoder.encode("user123"), Set.of(userRole));

        userRepository.save(admin);
        userRepository.save(user);
    }
}