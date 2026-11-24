package com.krishna.service;

import com.krishna.entity.Student;
import com.krishna.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class StudentProfileService {

    private final StudentRepository studentRepository;

    public StudentProfileService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getMyProfile(String username) {
        return studentRepository.findByUser_Username(username)
                .orElseThrow(() ->
                        new RuntimeException("Student profile not found"));
    }
}