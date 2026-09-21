# Smart Campus Student Management System

This is a simple Java console application made for the Smart Campus Student Management System coursework.

The system is used to manage students, lecturers, courses, departments, attendance and academic results.

## Main Features

The system has three types of users:

* Admin
* Lecturer
* Student

The admin can add departments, courses, lecturers and students.

The lecturer can record attendance and enter student marks.

The student can register for courses and view attendance, results and GPA.

## Technologies Used

* Java
* Java OOP
* CSV files
* VS Code

No database or extra libraries are required.

## How to Run in VS Code

1. Open the `SmartCampus` folder in VS Code.
2. Make sure the Java Extension Pack is installed.
3. Open `src/com/smartcampus/Main.java`.
4. Click the **Run** button above the `main` method.

## How to Run in Terminal

Open the terminal inside the `SmartCampus` folder and run:

```bash
javac -d out $(find src -name "*.java")
```

Then run:

```bash
java -cp out com.smartcampus.Main
```

## Login

The default admin account is:

```text
Username: admin
Password: admin123
```

After logging in as admin, you can add the information needed to test the system.

## Testing the System

A simple way to test the system is to first login as admin and create a department, course, lecturer and student.

Then login as the lecturer to record attendance and enter marks.

Finally, login as the student to register for a course and check attendance, results and GPA.

## Project Folders

The main folders are:

* `model` - contains the main classes used in the system.
* `service` - contains the main system operations.
* `util` - contains helper classes.
* `data` - contains the CSV files used to save information.

`Main.java` contains the main program and menus.

## Grading

The system currently uses:

* Coursework: 30%
* Exam: 70%

The grades are from A to F and the system also calculates the student's GPA.

## Note

This project is mainly for learning and coursework purposes. It uses CSV files for storing data instead of a real database.
