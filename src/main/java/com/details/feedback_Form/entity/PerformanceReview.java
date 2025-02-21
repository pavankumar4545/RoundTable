package com.details.feedback_Form.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "performance_review")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String name;  // Store name for convenience

    private int startMonth;
    private int startYear;
    private int endMonth;
    private int endYear;

    private int performanceRating;
    private String comment;

    private boolean submitEnabled;

    public PerformanceReview() {
    }

    public PerformanceReview(Long id, Employee employee, String name, int startMonth, int startYear, int endMonth, int endYear, int performanceRating, String comment, boolean submitEnabled) {
        this.id = id;
        this.employee = employee;
        this.name = name;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.performanceRating = performanceRating;
        this.comment = comment;
        this.submitEnabled = submitEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(int startMonth) {
        this.startMonth = startMonth;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(int performanceRating) {
        this.performanceRating = performanceRating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSubmitEnabled() {
        return submitEnabled;
    }

    public void setSubmitEnabled(boolean submitEnabled) {
        this.submitEnabled = submitEnabled;
    }
}

