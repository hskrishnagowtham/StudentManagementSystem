package com.krishna.controller;

import com.krishna.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class QuestionGeneratorController {

    private final GeminiService geminiService;

    public QuestionGeneratorController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/generate-questions")
    public String generateQuestions(@RequestBody QuestionRequest request) {
        return geminiService.generateQuestions(request.topic());
    }

    public record QuestionRequest(String topic) {
    }
}