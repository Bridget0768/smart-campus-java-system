package com.smartcampus.service;

import com.smartcampus.model.Course;
import com.smartcampus.model.Mark;
import com.smartcampus.util.CsvUtil;

import java.util.ArrayList;
import java.util.List;

public class GradeService {

    private static final String FILE = "data/marks.csv";

    private static final double COURSEWORK_WEIGHT = 0.30;
    private static final double EXAM_WEIGHT = 0.70;

    private final AcademicService academicService;

    public GradeService(AcademicService academicService) {
        this.academicService = academicService;
        CsvUtil.ensureFile(FILE, "studentNumber,courseCode,courseworkScore,examScore,total,grade,gradePoint");
    }

    public static String letterGradeFor(double total) {
        if (total >= 80) return "A";
        if (total >= 70) return "B";
        if (total >= 60) return "C";
        if (total >= 50) return "D";
        return "F";
    }

    public static double gradePointFor(String letter) {
        switch (letter) {
            case "A": return 5.0;
            case "B": return 4.0;
            case "C": return 3.0;
            case "D": return 2.0;
            default:  return 0.0;
        }
    }

    public Mark enterMark(String studentNumber, String courseCode, double courseworkScore, double examScore) {
        double total = (courseworkScore * COURSEWORK_WEIGHT) + (examScore * EXAM_WEIGHT);
        String grade = letterGradeFor(total);
        double gradePoint = gradePointFor(grade);

        List<String[]> rows = CsvUtil.readRows(FILE);
        rows.removeIf(r -> r[0].equals(studentNumber) && r[1].equalsIgnoreCase(courseCode));
        rows.add(new String[]{
                studentNumber, courseCode,
                String.valueOf(courseworkScore), String.valueOf(examScore),
                String.valueOf(total), grade, String.valueOf(gradePoint)
        });
        CsvUtil.writeAll(FILE, "studentNumber,courseCode,courseworkScore,examScore,total,grade,gradePoint", rows);

        return new Mark(studentNumber, courseCode, courseworkScore, examScore, total, grade, gradePoint);
    }

    public List<Mark> listAll() {
        List<Mark> list = new ArrayList<>();
        for (String[] r : CsvUtil.readRows(FILE)) {
            list.add(new Mark(r[0], r[1],
                    Double.parseDouble(r[2]), Double.parseDouble(r[3]),
                    Double.parseDouble(r[4]), r[5], Double.parseDouble(r[6])));
        }
        return list;
    }

    public List<Mark> forStudent(String studentNumber) {
        List<Mark> list = new ArrayList<>();
        for (Mark m : listAll()) {
            if (m.getStudentNumber().equals(studentNumber)) list.add(m);
        }
        return list;
    }

    public List<Mark> forCourse(String courseCode) {
        List<Mark> list = new ArrayList<>();
        for (Mark m : listAll()) {
            if (m.getCourseCode().equalsIgnoreCase(courseCode)) list.add(m);
        }
        return list;
    }

    public double gpaFor(String studentNumber) {
        List<Mark> marks = forStudent(studentNumber);
        if (marks.isEmpty()) return 0.0;

        double totalPoints = 0;
        double totalCredits = 0;
        for (Mark m : marks) {
            Course c = academicService.findCourse(m.getCourseCode());
            int credits = (c != null) ? c.getCreditUnits() : 1;
            totalPoints += m.getGradePoint() * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }
}
