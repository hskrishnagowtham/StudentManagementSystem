
package com.krishna.service;

import com.krishna.entity.Student;
import com.krishna.entity.User;
import com.krishna.repository.StudentRepository;
import com.krishna.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentLinkService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public StudentLinkService(
            StudentRepository studentRepository,
            UserRepository userRepository) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String linkStudentToUser(int rollNumber, String username) {

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!"USER".equals(user.getRole())) {
            throw new RuntimeException(
                    "Only USER accounts can be linked to students");
        }

        if (student.getUser() != null) {
            throw new RuntimeException(
                    "Student is already linked to an account");
        }

        if (studentRepository.existsByUser_Id(user.getId())) {
            throw new RuntimeException(
                    "User is already linked to another student");
        }

        student.setUser(user);

        studentRepository.save(student);

        return "Student linked successfully";
    }
}
