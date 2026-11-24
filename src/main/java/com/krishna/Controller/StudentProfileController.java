
package com.krishna.controller;

import com.krishna.dto.StudentResponse;
import com.krishna.entity.Student;
import com.krishna.service.StudentProfileService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(
            StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @GetMapping("/my-profile")
    public ResponseEntity<StudentResponse> getMyProfile(
            Authentication authentication) {

        String username = authentication.getName();

        Student student =
                studentProfileService.getMyProfile(username);

        StudentResponse response = new StudentResponse(
                student.getRollNumber(),
                student.getName(),
                student.getYear(),
                student.getSemester(),
                student.getCourse(),
                student.getTotalMarks(),
                student.getMaximumMarks(),
                student.getPercentage(),
                student.getGrade()
        );

        return ResponseEntity.ok(response);
    }
}
