package com.details.feedback_Form.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "rt_cycle")
public class RTCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int startMonth;

    @Column(nullable = false)
    private int startYear;

    @Column(nullable = false)
    private int endMonth;

    @Column(nullable = false)
    private int endYear;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
    }

    public RTCycle() {
    }

    public RTCycle(Long id, int startMonth, int startYear, int endMonth, int endYear, LocalDate createdAt) {
        this.id = id;
        this.startMonth = startMonth;
        this.startYear = startYear;
        this.endMonth = endMonth;
        this.endYear = endYear;
        this.createdAt = createdAt;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}

