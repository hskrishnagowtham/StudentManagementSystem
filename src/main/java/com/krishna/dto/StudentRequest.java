package com.krishna.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class StudentRequest {

    @Min(value = 1, message = "Roll number must be at least 1")
    private int rollNumber;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 1, message = "Year must be at least 1")
    @Max(value = 4, message = "Year cannot be greater than 4")
    private int year;

    @Min(value = 1, message = "Semester must be at least 1")
    @Max(value = 8, message = "Semester cannot be greater than 8")
    private int semester;

    @NotBlank(message = "Course cannot be empty")
    private String course;

    @Min(value = 0, message = "Total marks cannot be negative")
    private int totalMarks;


    // No-argument constructor
    public StudentRequest() {
    }


    // Parameterized constructor
    public StudentRequest(int rollNumber, String name, int year,
                          int semester, String course,
                          int totalMarks) {

        this.rollNumber = rollNumber;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.course = course;
        this.totalMarks = totalMarks;
    }


    // Getters and Setters

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
}

