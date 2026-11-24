
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE STUDENT
    public StudentResponse addStudent(StudentRequest request) {

        logger.info("Creating student with roll number: {}",
                request.getRollNumber());

        if (studentRepository.existsById(request.getRollNumber())) {
            throw new StudentAlreadyExistsException(
                    "Student with roll number "
                            + request.getRollNumber()
                            + " already exists"
            );
        }

        validateMarks(request);

        Student student = new Student();

        student.setRollNumber(request.getRollNumber());
        student.setName(request.getName());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        student.setCourse(request.getCourse());
        student.setTotalMarks(request.getTotalMarks());
        student.setMaximumMarks(request.getMaximumMarks());

        double percentage = calculatePercentage(
                request.getTotalMarks(),
                request.getMaximumMarks()
        );

        String grade = calculateGrade(percentage);

        student.setPercentage(percentage);
        student.setGrade(grade);

        Student savedStudent = studentRepository.save(student);

        logger.info("Student created successfully: {}",
                savedStudent.getRollNumber());

        return convertToResponse(savedStudent);
    }

    // GET ALL STUDENTS
    public List<StudentResponse> getAllStudents() {

        logger.info("Fetching all students");

        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET MY PROFILE
    @Transactional(readOnly = true)
    public StudentResponse getMyProfile(String username) {

        logger.info("Fetching student profile for user: {}", username);

        Student student = studentRepository
                .findByUser_Username(username)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "No student profile found for username: "
                                        + username
                        )
                );

        return convertToResponse(student);
    }

    // SEARCH STUDENTS BY NAME
    public List<StudentResponse> searchStudentsByName(String name) {

        logger.info("Searching students by name: {}", name);

        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET STUDENT BY ROLL NUMBER
    public StudentResponse getStudentById(int rollNumber) {

        logger.info("Fetching student with roll number: {}", rollNumber);

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() ->
                        new StudentNotFoundException(rollNumber)
                );

        return convertToResponse(student);
    }

    // UPDATE STUDENT
    public StudentResponse updateStudent(
            int rollNumber,
            StudentRequest request) {

        logger.info("Updating student with roll number: {}", rollNumber);

        Student student = studentRepository.findById(rollNumber)
                .orElseThrow(() ->
                        new StudentNotFoundException(rollNumber)
                );

        validateMarks(request);

        student.setName(request.getName());
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        student.setCourse(request.getCourse());
        student.setTotalMarks(request.getTotalMarks());
        student.setMaximumMarks(request.getMaximumMarks());

        double percentage = calculatePercentage(
                request.getTotalMarks(),
                request.getMaximumMarks()
        );

        String grade = calculateGrade(percentage);

        student.setPercentage(percentage);
        student.setGrade(grade);

        Student updatedStudent = studentRepository.save(student);

        logger.info("Student {} updated successfully", rollNumber);

        return convertToResponse(updatedStudent);
    }

    // DELETE STUDENT
    public void deleteStudent(int rollNumber) {

        logger.info("Deleting student with roll number: {}", rollNumber);

        if (!studentRepository.existsById(rollNumber)) {
            throw new StudentNotFoundException(rollNumber);
        }

        studentRepository.deleteById(rollNumber);

        logger.info("Student {} deleted successfully", rollNumber);
    }

    // VALIDATE MARKS
    private void validateMarks(StudentRequest request) {

        if (request.getMaximumMarks() <= 0) {
            throw new IllegalArgumentException(
                    "Maximum marks must be greater than zero"
            );
        }

        if (request.getTotalMarks() < 0) {
            throw new IllegalArgumentException(
                    "Total marks cannot be negative"
            );
        }

        if (request.getTotalMarks() > request.getMaximumMarks()) {
            throw new IllegalArgumentException(
                    "Total marks cannot exceed maximum marks"
            );
        }
    }

    // CALCULATE PERCENTAGE
    private double calculatePercentage(
            int totalMarks,
            int maximumMarks) {

        return ((double) totalMarks / maximumMarks) * 100;
    }

    // CALCULATE GRADE
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

    // CONVERT ENTITY TO RESPONSE DTO
    private StudentResponse convertToResponse(Student student) {

        return new StudentResponse(
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
    }
}
