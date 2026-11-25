
package com.krishna.Controller;

import com.krishna.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class GeminiTestController {

    private final GeminiService geminiService;

    public GeminiTestController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/test")
    public String testGemini(@RequestBody GeminiRequest request) {
        return geminiService.askGemini(request.prompt());
    }

    @PostMapping("/student-assistant")
    public String studentAssistant(@RequestBody GeminiRequest request) {

        System.out.println("AI CONTROLLER REACHED");
        System.out.println("Question: " + request.prompt());

        String response = geminiService.askStudentAssistant(request.prompt());

        System.out.println("GEMINI RESPONSE RECEIVED");

        return response;
    }

    public record GeminiRequest(String prompt) {
    }
}
