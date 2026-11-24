
package com.krishna.service;

import com.krishna.dto.RegisterRequest;
import com.krishna.entity.Student;
import com.krishna.entity.User;
import com.krishna.repository.StudentRepository;
import com.krishna.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.krishna.exception.DuplicateUsernameException;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            UserRepository userRepository,
            StudentRepository studentRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerStudent(RegisterRequest request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateUsernameException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        int nextRollNumber = studentRepository
                .findTopByOrderByRollNumberDesc()
                .map(student -> student.getRollNumber() + 1)
                .orElse(1);

        Student student = new Student();
        student.setRollNumber(nextRollNumber);
        student.setName(request.getName());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        student.setCourse(request.getCourse());
        student.setUser(user);

        studentRepository.save(student);
    }
}
