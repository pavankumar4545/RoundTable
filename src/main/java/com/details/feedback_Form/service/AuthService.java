package com.details.feedback_Form.service;


import com.details.feedback_Form.entity.Admin;
import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.repository.AdminRepository;
import com.details.feedback_Form.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Object validateUser(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            return admin.get();
        }

        Optional<Employee> employee = employeeRepository.findByEmail(email);
        if (employee.isPresent() && employee.get().getPassword().equals(password)) {
            return employee.get();
        }

        return null;
    }
}

