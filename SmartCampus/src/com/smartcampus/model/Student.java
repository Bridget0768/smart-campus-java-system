package com.smartcampus.model;

public class Student extends User {

    private String studentNumber;
    private String program;
    private int yearOfStudy;

    public Student(String username, String password, String fullName,
                    String studentNumber, String program, int yearOfStudy) {
        super(username, password, fullName);
        this.studentNumber = studentNumber;
        this.program = program;
        this.yearOfStudy = yearOfStudy;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getProgram() {
        return program;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }
}
