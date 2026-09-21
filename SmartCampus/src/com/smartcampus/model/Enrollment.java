package com.smartcampus.model;

public class Enrollment {

    private String studentNumber;
    private String courseCode;

    public Enrollment(String studentNumber, String courseCode) {
        this.studentNumber = studentNumber;
        this.courseCode = courseCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }
}
