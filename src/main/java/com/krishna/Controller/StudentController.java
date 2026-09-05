package com.krishna.controller;

import com.krishna.dto.StudentRequest;
import com.krishna.dto.StudentResponse;
import com.krishna.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Create student
    @PostMapping
    public ResponseEntity<StudentResponse> addStudent(
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.addStudent(request);

        return ResponseEntity.status(201).body(response);
    }

    // Get all students
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents()
        );
    }

    // Search students by name
    @GetMapping("/search")
    public ResponseEntity<List<StudentResponse>> searchStudents(
            @RequestParam String name) {
        return ResponseEntity.ok(
                studentService.searchStudentsByName(name)
        );
    }

    // Get student by roll number
    @GetMapping("/{rollNumber}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable int rollNumber) {

        return ResponseEntity.ok(
                studentService.getStudentById(rollNumber)
        );
    }

    // Update student
    @PutMapping("/{rollNumber}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable int rollNumber,
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.updateStudent(
                        rollNumber,
                        request
                );

        return ResponseEntity.ok(response);
    }

    // Delete student
    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable int rollNumber) {

        studentService.deleteStudent(rollNumber);

        return ResponseEntity.noContent().build();
    }
}