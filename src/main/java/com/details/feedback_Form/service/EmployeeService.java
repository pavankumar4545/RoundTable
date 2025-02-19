package com.details.feedback_Form.service;

import com.details.feedback_Form.entity.Employee;

public interface EmployeeService {
    Employee authenticateEmployee(String email, String password);
}

