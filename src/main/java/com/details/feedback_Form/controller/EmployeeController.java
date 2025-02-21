package com.details.feedback_Form.controller;

import com.details.feedback_Form.dtos.EmployeeLoginRequest;
import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody EmployeeLoginRequest request) {
        Employee employee = employeeService.authenticateEmployee(request.getEmail(), request.getPassword());

        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        // Ensure the role is always "employee"
        employee.setRole("employee");

        // Save the employee
        Employee savedEmployee = employeeService.createEmployee(employee);

        return ResponseEntity.ok().body("{\"message\": \"Employee created successfully!\"}");
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(404).body("Employee not found");
        }
    }
}
