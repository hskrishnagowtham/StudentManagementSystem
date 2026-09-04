package com.krishna.controller;

import com.krishna.dto.LoginRequest;
import com.krishna.dto.LoginResponse;
import com.krishna.dto.RegisterRequest;
import com.krishna.entity.User;
import com.krishna.service.UserService;
import com.krishna.util.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================
    // REGISTER
    // ============================

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {

        if (userService.findByUsername(
                request.getUsername()) != null) {

            return ResponseEntity
                    .badRequest()
                    .body("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        // Encrypt password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(request.getRole());

        userService.register(user);

        return ResponseEntity.ok(
                "User registered successfully: "
                        + request.getUsername()
        );
    }

    // ============================
    // LOGIN
    // ============================

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        User user = userService.findByUsername(
                request.getUsername()
        );

        // User doesn't exist
        if (user == null) {

            return ResponseEntity
                    .status(401)
                    .body(
                            new LoginResponse(
                                    "Invalid username or password",
                                    null,
                                    null
                            )
                    );
        }

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return ResponseEntity
                    .status(401)
                    .body(
                            new LoginResponse(
                                    "Invalid username or password",
                                    null,
                                    null
                            )
                    );
        }

        // Create UserDetails
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();

        // Generate JWT
        String token =
                jwtService.generateToken(userDetails);

        // Return response
        return ResponseEntity.ok(
                new LoginResponse(
                        "Login successful",
                        user.getUsername(),
                        token
                )
        );
    }
}