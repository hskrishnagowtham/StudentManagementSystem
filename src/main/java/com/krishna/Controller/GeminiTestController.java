
package com.krishna.Controller;

import com.krishna.service.GeminiService;
import com.krishna.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class GeminiTestController {

    private final GeminiService geminiService;
    private final StudentRepository studentRepository;

    public GeminiTestController(
            GeminiService geminiService,
            StudentRepository studentRepository) {
        this.geminiService = geminiService;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/test")
    public String testGemini(@RequestBody GeminiRequest request) {
        return geminiService.askGemini(request.prompt());
    }

    @PostMapping("/student-assistant")
    public String studentAssistant(@RequestBody GeminiRequest request) {
        return geminiService.askStudentAssistant(request.prompt());
    }

    public record GeminiRequest(String prompt) {
    }
}
