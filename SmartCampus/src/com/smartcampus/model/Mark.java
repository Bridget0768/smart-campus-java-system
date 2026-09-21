package com.smartcampus.model;

public class Mark {

    private String studentNumber;
    private String courseCode;
    private double courseworkScore;
    private double examScore;
    private double total;
    private String grade;
    private double gradePoint;

    public Mark(String studentNumber, String courseCode, double courseworkScore,
                double examScore, double total, String grade, double gradePoint) {
        this.studentNumber = studentNumber;
        this.courseCode = courseCode;
        this.courseworkScore = courseworkScore;
        this.examScore = examScore;
        this.total = total;
        this.grade = grade;
        this.gradePoint = gradePoint;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public double getCourseworkScore() {
        return courseworkScore;
    }

    public double getExamScore() {
        return examScore;
    }

    public double getTotal() {
        return total;
    }

    public String getGrade() {
        return grade;
    }

    public double getGradePoint() {
        return gradePoint;
    }
}
