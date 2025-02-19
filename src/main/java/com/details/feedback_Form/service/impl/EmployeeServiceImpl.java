package com.details.feedback_Form.service.impl;

import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.repository.EmployeeRepository;
import com.details.feedback_Form.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee authenticateEmployee(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);

        if (employee.isPresent()) {
            // Manually checking password since we are not using Spring Security
            if (employee.get().getPassword().equals(password)) {
                return employee.get(); // Login success
            }
        }
        return null; // Login failed
    }
}

