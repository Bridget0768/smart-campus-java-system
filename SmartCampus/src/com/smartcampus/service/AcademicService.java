package com.smartcampus.service;

import com.smartcampus.model.Course;
import com.smartcampus.model.Department;
import com.smartcampus.util.CsvUtil;

import java.util.ArrayList;
import java.util.List;

public class AcademicService {

    private static final String DEPARTMENTS_FILE = "data/departments.csv";
    private static final String COURSES_FILE = "data/courses.csv";

    public AcademicService() {
        CsvUtil.ensureFile(DEPARTMENTS_FILE, "code,name");
        CsvUtil.ensureFile(COURSES_FILE, "code,title,creditUnits,departmentCode,lecturerUsername");
    }

    public void addDepartment(String code, String name) {
        CsvUtil.appendRow(DEPARTMENTS_FILE, code, name);
    }

    public List<Department> listDepartments() {
        List<Department> list = new ArrayList<>();
        for (String[] r : CsvUtil.readRows(DEPARTMENTS_FILE)) {
            list.add(new Department(r[0], r[1]));
        }
        return list;
    }

    public boolean departmentExists(String code) {
        for (Department d : listDepartments()) if (d.getCode().equalsIgnoreCase(code)) return true;
        return false;
    }

    public void addCourse(String code, String title, int creditUnits, String deptCode) {
        CsvUtil.appendRow(COURSES_FILE, code, title, String.valueOf(creditUnits), deptCode, "");
    }

    public List<Course> listCourses() {
        List<Course> list = new ArrayList<>();
        for (String[] r : CsvUtil.readRows(COURSES_FILE)) {
            list.add(new Course(r[0], r[1], Integer.parseInt(r[2]), r[3], r[4]));
        }
        return list;
    }

    public Course findCourse(String code) {
        for (Course c : listCourses()) if (c.getCode().equalsIgnoreCase(code)) return c;
        return null;
    }

    public boolean courseExists(String code) {
        return findCourse(code) != null;
    }

    public List<Course> coursesForLecturer(String lecturerUsername) {
        List<Course> list = new ArrayList<>();
        for (Course c : listCourses()) {
            if (c.getLecturerUsername().equals(lecturerUsername)) list.add(c);
        }
        return list;
    }

    public void assignLecturer(String courseCode, String lecturerUsername) {
        List<String[]> rows = CsvUtil.readRows(COURSES_FILE);
        for (String[] r : rows) {
            if (r[0].equalsIgnoreCase(courseCode)) {
                r[4] = lecturerUsername;
            }
        }
        CsvUtil.writeAll(COURSES_FILE, "code,title,creditUnits,departmentCode,lecturerUsername", rows);
    }
}
