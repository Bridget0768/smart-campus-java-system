package com.smartcampus.model;

public abstract class User {

    private String username;
    private String password;
    private String fullName;

    public User(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean checkPassword(String attempt) {
        return password != null && password.equals(attempt);
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return getRole() + ": " + fullName + " (" + username + ")";
    }
}
