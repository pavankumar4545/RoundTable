package com.details.feedback_Form.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employee")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String role;  // "employee"

    @Column(nullable = false)
    private String bandLevel; // B6, B7, B8

    @Column(nullable = false)
    private String password;

    public Employee() {
    }

    public Employee(Long id, String email, String name, String role, String bandLevel, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.role = role;
        this.bandLevel = bandLevel;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBandLevel() {
        return bandLevel;
    }

    public void setBandLevel(String bandLevel) {
        this.bandLevel = bandLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

