package com.smartcampus.model;

public class Course {

    private String code;
    private String title;
    private int creditUnits;
    private String departmentCode;
    private String lecturerUsername;

    public Course(String code, String title, int creditUnits,
                  String departmentCode, String lecturerUsername) {
        this.code = code;
        this.title = title;
        this.creditUnits = creditUnits;
        this.departmentCode = departmentCode;
        this.lecturerUsername = lecturerUsername == null ? "" : lecturerUsername;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCreditUnits() {
        return creditUnits;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getLecturerUsername() {
        return lecturerUsername;
    }

    public void setLecturerUsername(String lecturerUsername) {
        this.lecturerUsername = lecturerUsername;
    }

    @Override
    public String toString() {
        String lect = lecturerUsername.isEmpty() ? "unassigned" : lecturerUsername;
        return code + " - " + title + " (" + creditUnits + " CU, lecturer: " + lect + ")";
    }
}
