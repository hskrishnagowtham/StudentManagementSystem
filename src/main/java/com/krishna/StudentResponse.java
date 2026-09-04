package com.krishna.dto;

public class StudentResponse {

    private int rollNumber;
    private String name;
    private int year;
    private int semester;
    private String course;
    private int totalMarks;
    private double percentage;
    private String grade;

    public StudentResponse() {
    }

    public StudentResponse(int rollNumber, String name, int year,
                           int semester, String course, int totalMarks,
                           double percentage, String grade) {

        this.rollNumber = rollNumber;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.course = course;
        this.totalMarks = totalMarks;
        this.percentage = percentage;
        this.grade = grade;
    }

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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}