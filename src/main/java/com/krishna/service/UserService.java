
package com.krishna.service;

import com.krishna.entity.User;
import com.krishna.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User register(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(user);
    }

    // Find user by username
    public User findByUsername(String username) {

        Optional<User> user =
                userRepository.findByUsername(username);

        return user.orElse(null);
    }

    // Find user by ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    // Reset existing ADMIN password
    public void resetAdminPassword(String newPassword) {

        User admin = userRepository.findByUsername("admin")
                .orElseThrow(() ->
                        new RuntimeException("Admin account not found"));

        admin.setPassword(passwordEncoder.encode(newPassword));
        admin.setRole("ADMIN");

        userRepository.save(admin);
    }
}
