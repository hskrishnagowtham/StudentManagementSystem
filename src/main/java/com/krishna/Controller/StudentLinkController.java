
package com.krishna.controller;

import com.krishna.dto.StudentLinkRequest;
import com.krishna.service.StudentLinkService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/students")
public class StudentLinkController {

    private final StudentLinkService studentLinkService;

    public StudentLinkController(
            StudentLinkService studentLinkService) {

        this.studentLinkService = studentLinkService;
    }

    @PostMapping("/link")
    public ResponseEntity<String> linkStudent(
            @Valid @RequestBody StudentLinkRequest request) {

        String message = studentLinkService.linkStudentToUser(
                request.getRollNumber(),
                request.getUsername()
        );

        return ResponseEntity.ok(message);
    }
}
