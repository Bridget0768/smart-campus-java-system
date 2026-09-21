package com.smartcampus.service;

import com.smartcampus.model.*;
import com.smartcampus.util.CsvUtil;

import java.util.List;

public class AuthService {

    private static final String ADMINS_FILE = "data/admins.csv";
    private static final String LECTURERS_FILE = "data/lecturers.csv";
    private static final String STUDENTS_FILE = "data/students.csv";

    public AuthService() {
        CsvUtil.ensureFile(ADMINS_FILE, "username,password,fullName");
        CsvUtil.ensureFile(LECTURERS_FILE, "username,password,fullName,employeeId,departmentCode");
        CsvUtil.ensureFile(STUDENTS_FILE, "username,password,fullName,studentNumber,program,yearOfStudy");

        if (CsvUtil.readRows(ADMINS_FILE).isEmpty()) {
            CsvUtil.appendRow(ADMINS_FILE, "admin", "admin123", "System Administrator");
            System.out.println("[first run] Created default admin login -> username: admin  password: admin123");
        }
    }

    public User login(String username, String password) {
        for (String[] r : CsvUtil.readRows(ADMINS_FILE)) {
            if (r[0].equals(username) && r[1].equals(password)) {
                return new Admin(r[0], r[1], r[2]);
            }
        }
        for (String[] r : CsvUtil.readRows(LECTURERS_FILE)) {
            if (r[0].equals(username) && r[1].equals(password)) {
                return new Lecturer(r[0], r[1], r[2], r[3], r[4]);
            }
        }
        for (String[] r : CsvUtil.readRows(STUDENTS_FILE)) {
            if (r[0].equals(username) && r[1].equals(password)) {
                return new Student(r[0], r[1], r[2], r[3], r[4], Integer.parseInt(r[5]));
            }
        }
        return null;
    }

    public boolean usernameTaken(String username) {
        for (String[] r : CsvUtil.readRows(ADMINS_FILE)) if (r[0].equals(username)) return true;
        for (String[] r : CsvUtil.readRows(LECTURERS_FILE)) if (r[0].equals(username)) return true;
        for (String[] r : CsvUtil.readRows(STUDENTS_FILE)) if (r[0].equals(username)) return true;
        return false;
    }

    public void addLecturer(Lecturer l) {
        CsvUtil.appendRow(LECTURERS_FILE, l.getUsername(), l.getPassword(), l.getFullName(),
                l.getEmployeeId(), l.getDepartmentCode());
    }

    public void addStudent(Student s) {
        CsvUtil.appendRow(STUDENTS_FILE, s.getUsername(), s.getPassword(), s.getFullName(),
                s.getStudentNumber(), s.getProgram(), String.valueOf(s.getYearOfStudy()));
    }

    public List<String[]> allStudentRows() {
        return CsvUtil.readRows(STUDENTS_FILE);
    }

    public List<String[]> allLecturerRows() {
        return CsvUtil.readRows(LECTURERS_FILE);
    }

    public Student findStudentByNumber(String studentNumber) {
        for (String[] r : CsvUtil.readRows(STUDENTS_FILE)) {
            if (r[3].equals(studentNumber)) {
                return new Student(r[0], r[1], r[2], r[3], r[4], Integer.parseInt(r[5]));
            }
        }
        return null;
    }
}
