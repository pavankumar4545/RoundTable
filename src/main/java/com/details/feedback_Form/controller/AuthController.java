package com.details.feedback_Form.controller;

import com.details.feedback_Form.entity.Admin;
import com.details.feedback_Form.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        Object user = authService.validateUser(email, password);
        if (user == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        System.out.println(" User Found: " + user.toString());

        // Prepare response
        Map<String, Object> response = new HashMap<>();
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            response.put("id", admin.getId());
            response.put("role", admin.getRole());
            response.put("name", admin.getName());
        }

        System.out.println(" Sending Response: " + response);

        return ResponseEntity.ok(response);
    }
}
