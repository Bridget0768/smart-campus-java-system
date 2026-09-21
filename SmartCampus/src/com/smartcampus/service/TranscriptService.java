package com.smartcampus.service;

import com.smartcampus.model.Course;
import com.smartcampus.model.Mark;
import com.smartcampus.model.Student;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class TranscriptService {

    private static final String OUTPUT_DIR = "data/transcripts";

    private final AcademicService academicService;
    private final GradeService gradeService;

    public TranscriptService(AcademicService academicService, GradeService gradeService) {
        this.academicService = academicService;
        this.gradeService = gradeService;
    }

    public String buildTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("======================================\n");
        sb.append("      ACADEMIC TRANSCRIPT\n");
        sb.append("======================================\n");
        sb.append("Name        : ").append(student.getFullName()).append("\n");
        sb.append("Student No. : ").append(student.getStudentNumber()).append("\n");
        sb.append("Program     : ").append(student.getProgram()).append("\n");
        sb.append("Year        : ").append(student.getYearOfStudy()).append("\n");
        sb.append("--------------------------------------\n");
        sb.append(String.format("%-10s %-25s %-6s %-6s %-6s%n", "Code", "Course", "Total", "Grade", "CU"));

        List<Mark> marks = gradeService.forStudent(student.getStudentNumber());
        for (Mark m : marks) {
            Course c = academicService.findCourse(m.getCourseCode());
            String title = (c != null) ? c.getTitle() : m.getCourseCode();
            int credits = (c != null) ? c.getCreditUnits() : 1;
            sb.append(String.format("%-10s %-25s %-6.1f %-6s %-6d%n",
                    m.getCourseCode(), title, m.getTotal(), m.getGrade(), credits));
        }

        sb.append("--------------------------------------\n");
        sb.append(String.format("CGPA: %.2f / 5.00%n", gradeService.gpaFor(student.getStudentNumber())));
        sb.append("======================================\n");
        return sb.toString();
    }

    public String saveTranscript(Student student) {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));
            String safeName = student.getStudentNumber().replaceAll("[\\\\/]", "_");
            String path = OUTPUT_DIR + "/" + safeName + ".txt";
            Files.write(Paths.get(path), buildTranscript(student).getBytes());
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Could not save transcript", e);
        }
    }
}
