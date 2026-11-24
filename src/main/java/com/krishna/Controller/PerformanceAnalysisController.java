
package com.krishna.controller;

import com.krishna.entity.Student;
import com.krishna.repository.StudentRepository;
import com.krishna.service.GeminiService;
import com.krishna.service.StudentProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class PerformanceAnalysisController {

    private final GeminiService geminiService;
    private final StudentProfileService studentProfileService;
    private final StudentRepository studentRepository;

    public PerformanceAnalysisController(
            GeminiService geminiService,
            StudentProfileService studentProfileService,
            StudentRepository studentRepository) {
        this.geminiService = geminiService;
        this.studentProfileService = studentProfileService;
        this.studentRepository = studentRepository;
    }

    @GetMapping("/performance")
    public String analyzeMyPerformance(Authentication authentication) {

        String role = authentication.getAuthorities()
                .stream()
                .map(authority -> authority.getAuthority())
                .filter(a -> a.equals("ROLE_ADMIN") || a.equals("ROLE_USER"))
                .findFirst()
                .orElse("");

        if ("ROLE_ADMIN".equals(role)) {

            List<Student> students = studentRepository.findAll();

            if (students.isEmpty()) {
                return "No student records are available for analysis.";
            }

            StringBuilder details = new StringBuilder();

            for (Student student : students) {
                details.append("""
                        
                        Student Name: %s
                        Course: %s
                        Year: %s
                        Semester: %s
                        Total Marks: %s
                        Maximum Marks: %s
                        Percentage: %s
                        Grade: %s
                        """.formatted(
                        student.getName(),
                        student.getCourse(),
                        student.getYear(),
                        student.getSemester(),
                        student.getTotalMarks(),
                        student.getMaximumMarks(),
                        student.getPercentage(),
                        student.getGrade()
                ));
            }

            return geminiService.analyzePerformance(
                    "Analyze the academic performance of all students. " +
                            "Provide an overall summary, identify trends only when supported " +
                            "by the data, and give practical recommendations.\n" + details
            );
        }

        if ("ROLE_USER".equals(role)) {

            Student student = studentProfileService
                    .getMyProfile(authentication.getName());

            String studentDetails = """
                    Name: %s
                    Course: %s
                    Year: %s
                    Semester: %s
                    Total Marks: %s
                    Maximum Marks: %s
                    Percentage: %s
                    Grade: %s
                    """.formatted(
                    student.getName(),
                    student.getCourse(),
                    student.getYear(),
                    student.getSemester(),
                    student.getTotalMarks(),
                    student.getMaximumMarks(),
                    student.getPercentage(),
                    student.getGrade()
            );

            return geminiService.analyzePerformance(studentDetails);
        }

        throw new IllegalArgumentException("Unsupported user role.");
    }
}
