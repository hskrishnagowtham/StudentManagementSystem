
package com.krishna.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatClient chatClient;

    public GeminiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // Basic AI request
    public String askGemini(String prompt) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            System.out.println("AI ERROR: " + e.getMessage());
            throw e;
        }
    }

    // AI Student Assistant
    public String askStudentAssistant(String question) {
        try {
            System.out.println("SENDING REQUEST TO GROQ...");

            String response = chatClient.prompt()
                    .system("""
                            You are a helpful AI Student Assistant.
                            Answer academic and programming questions
                            in simple language.
                            Keep answers short and clear.
                            Use small examples when needed.
                            Limit answers to approximately 200 words.
                            """)
                    .user(question)
                    .call()
                    .content();

            System.out.println("GROQ API CALL COMPLETED");

            return response;

        } catch (Exception e) {
            System.out.println("AI ERROR: " + e.getMessage());
            throw e;
        }
    }

    // AI Performance Analysis
    public String analyzePerformance(String studentDetails) {
        try {
            System.out.println("PERFORMANCE ANALYSIS: Sending request to Groq...");

            String response = chatClient.prompt()
                    .system("""
                            You are a careful AI Academic Performance Analyst.

                            Analyze the student's academic performance
                            using ONLY the information provided.

                            Never invent marks, attendance, subjects,
                            exam results, academic history, or personal information.

                            Do not assume failure based only on percentage.
                            Use the supplied grade as given.

                            If information is missing, mention
                            "Insufficient data".

                            Give practical and encouraging recommendations.
                            Do not make assumptions about graduation or placements.

                            Keep the report within 200 words.
                            """)
                    .user("Analyze this student's performance:\n" + studentDetails)
                    .call()
                    .content();

            System.out.println("PERFORMANCE ANALYSIS: Groq response received.");

            return response;

        } catch (Exception e) {
            System.out.println("PERFORMANCE ANALYSIS ERROR: " + e.getMessage());
            throw e;
        }
    }

    // AI Question Generator
    public String generateQuestions(String topic) {
        return chatClient.prompt()
                .system("""
                        You are an AI Question Generator for college students.

                        Generate 10 useful practice questions
                        based on the topic provided.

                        Include:
                        4 basic questions
                        3 intermediate questions
                        3 advanced questions

                        Keep questions clear and educational.
                        Number each question.
                        Do not provide answers unless requested.
                        """)
                .user("Generate practice questions on: " + topic)
                .call()
                .content();
    }

    // AI Learning Recommendations
    public String generateLearningRecommendations(String studentDetails) {
        return chatClient.prompt()
                .system("""
                        You are an expert AI Learning and Career
                        Recommendation Assistant for college students.

                        Analyze the student's details and create
                        a personalized learning plan.

                        Use only the provided information.
                        Never invent marks, grades, attendance,
                        achievements, or skills.

                        If information is missing, mention
                        "Insufficient data".

                        Do not claim completed courses or projects
                        unless explicitly mentioned.

                        Give realistic recommendations.
                        Do not use Markdown tables.

                        Include these sections:

                        1. Personalized Learning Plan
                        2. High-Priority Technical Skills
                        3. Java Full Stack Development
                        4. Project Development
                        5. Placement Preparation
                        6. Weekly Study Schedule
                        7. 12-Week Learning Roadmap
                        8. Final Recommendations
                        """)
                .user("Create a personalized learning plan using these student details:\n"
                        + studentDetails)
                .call()
                .content();
    }
}
