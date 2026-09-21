package com.smartcampus.model;

public class AttendanceRecord {

    private String studentNumber;
    private String courseCode;
    private String date;
    private String status;

    public AttendanceRecord(String studentNumber, String courseCode, String date, String status) {
        this.studentNumber = studentNumber;
        this.courseCode = courseCode;
        this.date = date;
        this.status = status;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
