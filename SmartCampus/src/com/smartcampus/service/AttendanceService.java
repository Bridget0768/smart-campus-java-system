package com.smartcampus.service;

import com.smartcampus.model.AttendanceRecord;
import com.smartcampus.util.CsvUtil;

import java.util.ArrayList;
import java.util.List;

public class AttendanceService {

    private static final String FILE = "data/attendance.csv";

    public AttendanceService() {
        CsvUtil.ensureFile(FILE, "studentNumber,courseCode,date,status");
    }

    public void record(String studentNumber, String courseCode, String date, String status) {
        CsvUtil.appendRow(FILE, studentNumber, courseCode, date, status);
    }

    public List<AttendanceRecord> listAll() {
        List<AttendanceRecord> list = new ArrayList<>();
        for (String[] r : CsvUtil.readRows(FILE)) {
            list.add(new AttendanceRecord(r[0], r[1], r[2], r[3]));
        }
        return list;
    }

    public List<AttendanceRecord> forStudent(String studentNumber) {
        List<AttendanceRecord> list = new ArrayList<>();
        for (AttendanceRecord a : listAll()) {
            if (a.getStudentNumber().equals(studentNumber)) list.add(a);
        }
        return list;
    }

    public List<AttendanceRecord> forStudentAndCourse(String studentNumber, String courseCode) {
        List<AttendanceRecord> list = new ArrayList<>();
        for (AttendanceRecord a : listAll()) {
            if (a.getStudentNumber().equals(studentNumber) && a.getCourseCode().equalsIgnoreCase(courseCode)) {
                list.add(a);
            }
        }
        return list;
    }

    public double attendancePercentage(String studentNumber, String courseCode) {
        List<AttendanceRecord> records = forStudentAndCourse(studentNumber, courseCode);
        if (records.isEmpty()) return 0.0;
        long present = records.stream().filter(r -> r.getStatus().equalsIgnoreCase("PRESENT")).count();
        return (present * 100.0) / records.size();
    }
}
