package com.details.feedback_Form.dtos;


public class EmployeeLoginRequest {
    private String email;
    private String password;

    // Constructors
    public EmployeeLoginRequest() {}

    public EmployeeLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
