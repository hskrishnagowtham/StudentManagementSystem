
package com.krishna.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    private final ChatClient chatClient;

    public GeminiService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String askGemini(String prompt) {
        try {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public String askStudentAssistant(String question) {

        try {
            System.out.println("SENDING REQUEST TO GEMINI...");

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

            System.out.println("GEMINI API CALL COMPLETED");

            return response;

        } catch (Exception e) {
            System.out.println("GEMINI API ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String analyzePerformance(String studentDetails) {

        try {
            System.out.println("PERFORMANCE ANALYSIS: Sending request to Gemini...");

            String response = chatClient.prompt()
                    .system("""
                            You are a careful AI Academic Performance Analyst.
                            
                            Analyze the student's academic performance using
                            ONLY the information provided.
                            
                            Never invent marks, attendance, subjects, exam results,
                            academic history, or personal information.
                            
                            Do not assume failure based only on percentage.
                            Use the supplied grade as given.
                            
                            If information is missing, mention "Insufficient data".
                            
                            Give practical and encouraging recommendations.
                            Do not make assumptions about graduation or placements.
                            
                            Keep the report within 200 words.
                            """)
                    .user("Analyze this student's performance:\n" + studentDetails)
                    .call()
                    .content();

            System.out.println("PERFORMANCE ANALYSIS: Gemini response received.");

            return response;

        } catch (Exception e) {
            System.out.println("PERFORMANCE ANALYSIS ERROR: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String generateQuestions(String topic) {

        return chatClient.prompt()
                .system("""
                        You are an AI Question Generator for college students.
                        Generate 10 useful practice questions based on the
                        topic provided by the student.
                        Include:
                        1. 4 basic questions
                        2. 3 intermediate questions
                        3. 3 advanced questions
                        
                        Keep the questions clear and educational.
                        Number each question.
                        Do not provide answers unless requested.
                        """)
                .user("Generate practice questions on: " + topic)
                .call()
                .content();
    }


    public String generateLearningRecommendations(String studentDetails) {

        return chatClient.prompt()
                .system("""
                        You are an expert AI Learning and Career Recommendation Assistant
                        for college students.
                        
                        Analyze the student's details and create a personalized learning plan.
                        
                        IMPORTANT RULES:
                        1. Use ONLY the academic and personal information provided.
                        2. Never invent marks, grades, attendance, achievements or skills.
                        3. If information is missing, write "Insufficient data".
                        4. Do not claim that the student has completed a course or project
                           unless it is explicitly mentioned.
                        5. Give realistic recommendations suitable for the student's level.
                        6. Do not generate Markdown tables.
                        7. Use headings, numbered lists and bullet points only.
                        8. Keep the language simple, clear and encouraging.
                        
                        ORGANIZE THE RESPONSE USING THESE SECTIONS:
                        
                        # Personalized Learning Plan
                        
                        ## 1. High-Priority Technical Skills
                        Recommend relevant programming and technical skills.
                        
                        ## 2. Java Full Stack Development
                        Cover Java, OOP, DSA, Spring Boot, REST APIs,
                        Spring Security, React, JavaScript and MySQL
                        where appropriate.
                        
                        ## 3. Project Development
                        Suggest practical projects that can strengthen
                        the student's portfolio.
                        
                        ## 4. Placement Preparation
                        Include coding practice, technical interviews,
                        resume improvement and communication skills.
                        
                        ## 5. Weekly Study Schedule
                        Provide a realistic weekly schedule.
                        
                        ## 6. 12-Week Learning Roadmap
                        Divide the roadmap into manageable phases.
                        
                        ## 7. Final Recommendations
                        Give the student three important next actions.
                        
                        Use short paragraphs and readable bullet points.
                        Avoid unnecessary advanced technologies.
                        Do not recommend learning too many technologies at once.
                        """)
                .user("Create a personalized learning plan using these student details:\n"
                        + studentDetails)
                .call()
                .content();
    }
}
