
package com.krishna.Controller;

import com.krishna.dto.LoginRequest;
import com.krishna.dto.LoginResponse;
import com.krishna.dto.RegisterRequest;

import com.krishna.entity.User;
import com.krishna.service.RegistrationService;
import com.krishna.service.UserService;
import com.krishna.util.JwtService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final RegistrationService registrationService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            RegistrationService registrationService,
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.registrationService = registrationService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        registrationService.registerStudent(request);

        return ResponseEntity.ok(
                "Student registered successfully: " + request.getUsername()
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        User user = userService.findByUsername(request.getUsername());

        // Check whether user exists
        if (user == null) {
            return ResponseEntity.status(401).body(
                    new LoginResponse(
                            "Invalid username or password",
                            null,
                            null,
                            null
                    )
            );
        }

        System.out.println("LOGIN DEBUG - username: "
                + user.getUsername()
                + ", role: " + user.getRole());

        // Check password
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return ResponseEntity.status(401).body(
                    new LoginResponse(
                            "Invalid username or password",
                            null,
                            null,
                            null
                    )
            );
        }

        // Create authenticated user details
        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();

        // Generate JWT token
        String token = jwtService.generateToken(userDetails);

        // Return login response
        return ResponseEntity.ok(
                new LoginResponse(
                        "Login successful",
                        user.getUsername(),
                        token,
                        user.getRole()
                )
        );
    }
}
