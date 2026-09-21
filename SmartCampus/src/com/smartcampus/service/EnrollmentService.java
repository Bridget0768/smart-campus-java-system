package com.smartcampus.service;

import com.smartcampus.model.Enrollment;
import com.smartcampus.util.CsvUtil;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {

    private static final String FILE = "data/enrollments.csv";

    public EnrollmentService() {
        CsvUtil.ensureFile(FILE, "studentNumber,courseCode");
    }

    public boolean isEnrolled(String studentNumber, String courseCode) {
        for (Enrollment e : listAll()) {
            if (e.getStudentNumber().equals(studentNumber) && e.getCourseCode().equalsIgnoreCase(courseCode)) {
                return true;
            }
        }
        return false;
    }

    public void enroll(String studentNumber, String courseCode) {
        CsvUtil.appendRow(FILE, studentNumber, courseCode);
    }

    public List<Enrollment> listAll() {
        List<Enrollment> list = new ArrayList<>();
        for (String[] r : CsvUtil.readRows(FILE)) {
            list.add(new Enrollment(r[0], r[1]));
        }
        return list;
    }

    public List<Enrollment> forStudent(String studentNumber) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : listAll()) {
            if (e.getStudentNumber().equals(studentNumber)) list.add(e);
        }
        return list;
    }

    public List<Enrollment> forCourse(String courseCode) {
        List<Enrollment> list = new ArrayList<>();
        for (Enrollment e : listAll()) {
            if (e.getCourseCode().equalsIgnoreCase(courseCode)) list.add(e);
        }
        return list;
    }
}
