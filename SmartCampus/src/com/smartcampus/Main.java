package com.smartcampus;

import com.smartcampus.model.*;
import com.smartcampus.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    private static final AuthService authService = new AuthService();
    private static final AcademicService academicService = new AcademicService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();
    private static final AttendanceService attendanceService = new AttendanceService();
    private static final GradeService gradeService = new GradeService(academicService);
    private static final TranscriptService transcriptService =
            new TranscriptService(academicService, gradeService);

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println(" SMART CAMPUS STUDENT MANAGEMENT SYSTEM");
        System.out.println("========================================");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("0. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                loginFlow();
            } else if (choice.equals("0")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }

    private static void loginFlow() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        System.out.print("Password: ");
        String password = sc.nextLine().trim();

        User user = authService.login(username, password);
        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("\nWelcome, " + user.getFullName() + " [" + user.getRole() + "]");

        if (user instanceof Admin) {
            adminMenu((Admin) user);
        } else if (user instanceof Lecturer) {
            lecturerMenu((Lecturer) user);
        } else if (user instanceof Student) {
            studentMenu((Student) user);
        }
    }

    private static void adminMenu(Admin admin) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- ADMIN MENU (" + admin.getFullName() + ") ---");
            System.out.println("1. Add Department");
            System.out.println("2. Add Course");
            System.out.println("3. Add Lecturer");
            System.out.println("4. Add Student");
            System.out.println("5. Assign Lecturer to Course");
            System.out.println("6. View All Departments");
            System.out.println("7. View All Courses");
            System.out.println("8. View All Students");
            System.out.println("9. View All Lecturers");
            System.out.println("10. System Report");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1": addDepartment(); break;
                case "2": addCourse(); break;
                case "3": addLecturer(); break;
                case "4": addStudent(); break;
                case "5": assignLecturer(); break;
                case "6": viewDepartments(); break;
                case "7": viewCourses(); break;
                case "8": viewStudents(); break;
                case "9": viewLecturers(); break;
                case "10": systemReport(); break;
                case "0": loggedIn = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addDepartment() {
        System.out.print("Department code (e.g. CS): ");
        String code = sc.nextLine().trim();
        System.out.print("Department name: ");
        String name = sc.nextLine().trim();
        academicService.addDepartment(code, name);
        System.out.println("Department added.");
    }

    private static void addCourse() {
        System.out.print("Course code (e.g. CS201): ");
        String code = sc.nextLine().trim();
        System.out.print("Course title: ");
        String title = sc.nextLine().trim();
        int credits = readInt("Credit units: ");
        System.out.print("Department code: ");
        String dept = sc.nextLine().trim();
        if (!academicService.departmentExists(dept)) {
            System.out.println("No such department. Add the department first.");
            return;
        }
        academicService.addCourse(code, title, credits, dept);
        System.out.println("Course added.");
    }

    private static void addLecturer() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        if (authService.usernameTaken(username)) {
            System.out.println("That username is already taken.");
            return;
        }
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        System.out.print("Full name: ");
        String fullName = sc.nextLine().trim();
        System.out.print("Employee ID: ");
        String empId = sc.nextLine().trim();
        System.out.print("Department code: ");
        String dept = sc.nextLine().trim();
        authService.addLecturer(new Lecturer(username, password, fullName, empId, dept));
        System.out.println("Lecturer added.");
    }

    private static void addStudent() {
        System.out.print("Username: ");
        String username = sc.nextLine().trim();
        if (authService.usernameTaken(username)) {
            System.out.println("That username is already taken.");
            return;
        }
        System.out.print("Password: ");
        String password = sc.nextLine().trim();
        System.out.print("Full name: ");
        String fullName = sc.nextLine().trim();
        System.out.print("Student number: ");
        String studentNo = sc.nextLine().trim();
        System.out.print("Program: ");
        String program = sc.nextLine().trim();
        int year = readInt("Year of study: ");
        authService.addStudent(new Student(username, password, fullName, studentNo, program, year));
        System.out.println("Student added.");
    }

    private static void assignLecturer() {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        if (!academicService.courseExists(code)) {
            System.out.println("No such course.");
            return;
        }
        System.out.print("Lecturer username: ");
        String lect = sc.nextLine().trim();
        academicService.assignLecturer(code, lect);
        System.out.println("Lecturer assigned.");
    }

    private static void viewDepartments() {
        List<Department> depts = academicService.listDepartments();
        if (depts.isEmpty()) { System.out.println("No departments yet."); return; }
        for (Department d : depts) System.out.println(" - " + d);
    }

    private static void viewCourses() {
        List<Course> courses = academicService.listCourses();
        if (courses.isEmpty()) { System.out.println("No courses yet."); return; }
        for (Course c : courses) System.out.println(" - " + c);
    }

    private static void viewStudents() {
        List<String[]> rows = authService.allStudentRows();
        if (rows.isEmpty()) { System.out.println("No students yet."); return; }
        for (String[] r : rows) {
            System.out.println(" - " + r[3] + " | " + r[2] + " | " + r[4] + " | Year " + r[5]);
        }
    }

    private static void viewLecturers() {
        List<String[]> rows = authService.allLecturerRows();
        if (rows.isEmpty()) { System.out.println("No lecturers yet."); return; }
        for (String[] r : rows) {
            System.out.println(" - " + r[3] + " | " + r[2] + " (" + r[0] + ") | Dept: " + r[4]);
        }
    }

    private static void systemReport() {
        System.out.println("Departments : " + academicService.listDepartments().size());
        System.out.println("Courses     : " + academicService.listCourses().size());
        System.out.println("Students    : " + authService.allStudentRows().size());
        System.out.println("Lecturers   : " + authService.allLecturerRows().size());
        System.out.println("Enrollments : " + enrollmentService.listAll().size());
        System.out.println("Marks Given : " + gradeService.listAll().size());
    }

    private static void lecturerMenu(Lecturer lecturer) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- LECTURER MENU (" + lecturer.getFullName() + ") ---");
            System.out.println("1. View My Courses");
            System.out.println("2. View Students Registered for a Course");
            System.out.println("3. Record Attendance");
            System.out.println("4. Enter Marks");
            System.out.println("5. View Course Performance");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1": viewMyCourses(lecturer); break;
                case "2": viewRegisteredStudents(); break;
                case "3": recordAttendance(); break;
                case "4": enterMarks(); break;
                case "5": viewCoursePerformance(); break;
                case "0": loggedIn = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewMyCourses(Lecturer lecturer) {
        List<Course> courses = academicService.coursesForLecturer(lecturer.getUsername());
        if (courses.isEmpty()) { System.out.println("No courses assigned to you yet."); return; }
        for (Course c : courses) System.out.println(" - " + c);
    }

    private static void viewRegisteredStudents() {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        List<Enrollment> list = enrollmentService.forCourse(code);
        if (list.isEmpty()) { System.out.println("No students registered for this course."); return; }
        for (Enrollment e : list) {
            Student s = authService.findStudentByNumber(e.getStudentNumber());
            System.out.println(" - " + e.getStudentNumber() + (s != null ? " | " + s.getFullName() : ""));
        }
    }

    private static void recordAttendance() {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        System.out.print("Student number: ");
        String studentNo = sc.nextLine().trim();
        System.out.print("Date (yyyy-MM-dd): ");
        String date = sc.nextLine().trim();
        System.out.print("Status (PRESENT/ABSENT): ");
        String status = sc.nextLine().trim().toUpperCase();
        attendanceService.record(studentNo, code, date, status);
        System.out.println("Attendance recorded.");
    }

    private static void enterMarks() {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        System.out.print("Student number: ");
        String studentNo = sc.nextLine().trim();
        double cw = readDouble("Coursework score (0-100): ");
        double exam = readDouble("Exam score (0-100): ");
        Mark m = gradeService.enterMark(studentNo, code, cw, exam);
        System.out.printf("Saved. Total: %.1f  Grade: %s%n", m.getTotal(), m.getGrade());
    }

    private static void viewCoursePerformance() {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        List<Mark> marks = gradeService.forCourse(code);
        if (marks.isEmpty()) { System.out.println("No marks entered for this course yet."); return; }
        double sum = 0;
        for (Mark m : marks) {
            System.out.printf(" - %-12s Total: %-6.1f Grade: %s%n", m.getStudentNumber(), m.getTotal(), m.getGrade());
            sum += m.getTotal();
        }
        System.out.printf("Class average: %.1f%n", sum / marks.size());
    }

    private static void studentMenu(Student student) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- STUDENT MENU (" + student.getFullName() + ") ---");
            System.out.println("1. View Profile");
            System.out.println("2. Register for a Course");
            System.out.println("3. View My Registered Courses");
            System.out.println("4. View My Attendance");
            System.out.println("5. View My Results / GPA");
            System.out.println("6. View / Save Transcript");
            System.out.println("0. Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();

            switch (c) {
                case "1": viewProfile(student); break;
                case "2": registerForCourse(student); break;
                case "3": viewMyCourses(student); break;
                case "4": viewMyAttendance(student); break;
                case "5": viewMyResults(student); break;
                case "6": viewTranscript(student); break;
                case "0": loggedIn = false; break;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewProfile(Student student) {
        System.out.println("Name          : " + student.getFullName());
        System.out.println("Student No.   : " + student.getStudentNumber());
        System.out.println("Program       : " + student.getProgram());
        System.out.println("Year of Study : " + student.getYearOfStudy());
    }

    private static void registerForCourse(Student student) {
        System.out.print("Course code: ");
        String code = sc.nextLine().trim();
        if (!academicService.courseExists(code)) {
            System.out.println("No such course.");
            return;
        }
        if (enrollmentService.isEnrolled(student.getStudentNumber(), code)) {
            System.out.println("You are already registered for this course.");
            return;
        }
        enrollmentService.enroll(student.getStudentNumber(), code);
        System.out.println("Registered successfully.");
    }

    private static void viewMyCourses(Student student) {
        List<Enrollment> list = enrollmentService.forStudent(student.getStudentNumber());
        if (list.isEmpty()) { System.out.println("You are not registered for any courses yet."); return; }
        for (Enrollment e : list) {
            Course c = academicService.findCourse(e.getCourseCode());
            System.out.println(" - " + (c != null ? c : e.getCourseCode()));
        }
    }

    private static void viewMyAttendance(Student student) {
        List<Enrollment> list = enrollmentService.forStudent(student.getStudentNumber());
        if (list.isEmpty()) { System.out.println("You are not registered for any courses yet."); return; }
        for (Enrollment e : list) {
            double pct = attendanceService.attendancePercentage(student.getStudentNumber(), e.getCourseCode());
            System.out.printf(" - %-10s Attendance: %.0f%%%n", e.getCourseCode(), pct);
        }
    }

    private static void viewMyResults(Student student) {
        List<Mark> marks = gradeService.forStudent(student.getStudentNumber());
        if (marks.isEmpty()) { System.out.println("No results yet."); return; }
        for (Mark m : marks) {
            System.out.printf(" - %-10s Total: %-6.1f Grade: %s%n", m.getCourseCode(), m.getTotal(), m.getGrade());
        }
        System.out.printf("Current CGPA: %.2f / 5.00%n", gradeService.gpaFor(student.getStudentNumber()));
    }

    private static void viewTranscript(Student student) {
        String transcript = transcriptService.buildTranscript(student);
        System.out.println(transcript);
        String path = transcriptService.saveTranscript(student);
        System.out.println("Saved to: " + path);
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
}
