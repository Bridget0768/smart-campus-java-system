package com.smartcampus.model;

public class Lecturer extends User {

    private String employeeId;
    private String departmentCode;

    public Lecturer(String username, String password, String fullName,
                     String employeeId, String departmentCode) {
        super(username, password, fullName);
        this.employeeId = employeeId;
        this.departmentCode = departmentCode;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    @Override
    public String getRole() {
        return "LECTURER";
    }
}
