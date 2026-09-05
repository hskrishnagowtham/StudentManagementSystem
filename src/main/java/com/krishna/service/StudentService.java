package com.krishna.service;

import com.krishna.dto.StudentRequest;
import com.krishna.dto.StudentResponse;
import com.krishna.entity.Student;
import com.krishna.exception.StudentAlreadyExistsException;
import com.krishna.exception.StudentNotFoundException;
import com.krishna.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    // Constructor Injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    // ==========================================
    // CREATE STUDENT
    // ==========================================

    public StudentResponse addStudent(StudentRequest request) {

        logger.info("Creating student with roll number: {}",
                request.getRollNumber());

        // Check duplicate roll number
        if (studentRepository.existsById(request.getRollNumber())) {

            logger.warn("Student with roll number {} already exists",
                    request.getRollNumber());

            throw new StudentAlreadyExistsException(
                    "Student with roll number "
                            + request.getRollNumber()
                            + " already exists"
            );
        }

        // Convert StudentRequest → Student
        Student student = new Student();

        student.setRollNumber(request.getRollNumber());
        student.setName(request.getName());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        student.setCourse(request.getCourse());
        student.setTotalMarks(request.getTotalMarks());

        // Calculate percentage and grade
        double percentage = calculatePercentage(request.getTotalMarks());
        String grade = calculateGrade(percentage);

        student.setPercentage(percentage);
        student.setGrade(grade);

        // Save to database
        Student savedStudent = studentRepository.save(student);

        logger.info(
                "Student created successfully with roll number: {}",
                savedStudent.getRollNumber()
        );

        // Convert Student → StudentResponse
        return convertToResponse(savedStudent);
    }


    // ==========================================
    // GET ALL STUDENTS
    // ==========================================

    public List<StudentResponse> getAllStudents() {

        logger.info("Fetching all students");

        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ==========================================
    // SEARCH STUDENTS BY NAME
    // ==========================================

    public List<StudentResponse> searchStudentsByName(String name) {

        logger.info("Searching students by name: {}", name);

        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    // ==========================================
    // GET STUDENT BY ROLL NUMBER
    // ==========================================

    public StudentResponse getStudentById(int rollNumber) {

        logger.info(
                "Fetching student with roll number: {}",
                rollNumber
        );

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> {

                    logger.warn(
                            "Student with roll number {} not found",
                            rollNumber
                    );

                    return new StudentNotFoundException(rollNumber);
                });

        return convertToResponse(student);
    }


    // ==========================================
    // UPDATE STUDENT
    // ==========================================

    public StudentResponse updateStudent(
            int rollNumber,
            StudentRequest request) {

        logger.info(
                "Updating student with roll number: {}",
                rollNumber
        );

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() -> {

                    logger.warn(
                            "Cannot update. Student {} not found",
                            rollNumber
                    );

                    return new StudentNotFoundException(rollNumber);
                });

        student.setName(request.getName());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        student.setCourse(request.getCourse());
        student.setTotalMarks(request.getTotalMarks());

        // Recalculate percentage and grade
        double percentage = calculatePercentage(request.getTotalMarks());
        String grade = calculateGrade(percentage);

        student.setPercentage(percentage);
        student.setGrade(grade);

        Student updatedStudent =
                studentRepository.save(student);

        logger.info(
                "Student {} updated successfully",
                rollNumber
        );

        return convertToResponse(updatedStudent);
    }


    // ==========================================
    // DELETE STUDENT
    // ==========================================

    public void deleteStudent(int rollNumber) {

        logger.info(
                "Deleting student with roll number: {}",
                rollNumber
        );

        if (!studentRepository.existsById(rollNumber)) {

            logger.warn(
                    "Cannot delete. Student {} not found",
                    rollNumber
            );

            throw new StudentNotFoundException(rollNumber);
        }

        studentRepository.deleteById(rollNumber);

        logger.info(
                "Student {} deleted successfully",
                rollNumber
        );
    }


    // ==========================================
    // CALCULATE PERCENTAGE
    // ==========================================

    private double calculatePercentage(int totalMarks) {

        // Assuming total marks are out of 1000
        return (totalMarks / 1000.0) * 100;
    }


    // ==========================================
    // CALCULATE GRADE
    // ==========================================

    private String calculateGrade(double percentage) {

        if (percentage >= 90) {
            return "A+";
        } else if (percentage >= 80) {
            return "A";
        } else if (percentage >= 70) {
            return "B";
        } else if (percentage >= 60) {
            return "C";
        } else if (percentage >= 50) {
            return "D";
        } else {
            return "F";
        }
    }


    // ==========================================
    // CONVERT ENTITY → RESPONSE DTO
    // ==========================================

    private StudentResponse convertToResponse(Student student) {

        return new StudentResponse(
                student.getRollNumber(),
                student.getName(),
                student.getYear(),
                student.getSemester(),
                student.getCourse(),
                student.getTotalMarks(),
                student.getPercentage(),
                student.getGrade()
        );
    }
}