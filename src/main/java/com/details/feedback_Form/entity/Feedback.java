package com.details.feedback_Form.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    private String managerEmail;

    private String month;
    private int year;

    // Fields for Band B6
    private Integer leadership;
    private Integer orgContribution;
    private Integer assistingInPreSales;

    // Fields for Band B7
    private Integer timelyDeliveryB7;
    private Integer codeQualityB7;
    private Integer clientCommunication;

    // Fields for Band B8
    private Integer timelyDeliveryB8;
    private Integer codeQualityB8;
    private Integer improvement;

    @Column(columnDefinition = "TEXT")
    private String comment;

    private String attachmentUrl;

    private String status;

    private LocalDate submissionDate = LocalDate.now();

    public Feedback() {
    }

    public Feedback(Long id, Employee employee, String managerEmail, String month, int year, Integer leadership, Integer orgContribution, Integer assistingInPreSales, Integer timelyDeliveryB7, Integer codeQualityB7, Integer clientCommunication, Integer timelyDeliveryB8, Integer codeQualityB8, Integer improvement, String comment, String attachmentUrl, String status, LocalDate submissionDate) {
        this.id = id;
        this.employee = employee;
        this.managerEmail = managerEmail;
        this.month = month;
        this.year = year;
        this.leadership = leadership;
        this.orgContribution = orgContribution;
        this.assistingInPreSales = assistingInPreSales;
        this.timelyDeliveryB7 = timelyDeliveryB7;
        this.codeQualityB7 = codeQualityB7;
        this.clientCommunication = clientCommunication;
        this.timelyDeliveryB8 = timelyDeliveryB8;
        this.codeQualityB8 = codeQualityB8;
        this.improvement = improvement;
        this.comment = comment;
        this.attachmentUrl = attachmentUrl;
        this.status = status;
        this.submissionDate = submissionDate;
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

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getLeadership() {
        return leadership;
    }

    public void setLeadership(Integer leadership) {
        this.leadership = leadership;
    }

    public Integer getOrgContribution() {
        return orgContribution;
    }

    public void setOrgContribution(Integer orgContribution) {
        this.orgContribution = orgContribution;
    }

    public Integer getAssistingInPreSales() {
        return assistingInPreSales;
    }

    public void setAssistingInPreSales(Integer assistingInPreSales) {
        this.assistingInPreSales = assistingInPreSales;
    }

    public Integer getTimelyDeliveryB7() {
        return timelyDeliveryB7;
    }

    public void setTimelyDeliveryB7(Integer timelyDeliveryB7) {
        this.timelyDeliveryB7 = timelyDeliveryB7;
    }

    public Integer getCodeQualityB7() {
        return codeQualityB7;
    }

    public void setCodeQualityB7(Integer codeQualityB7) {
        this.codeQualityB7 = codeQualityB7;
    }

    public Integer getClientCommunication() {
        return clientCommunication;
    }

    public void setClientCommunication(Integer clientCommunication) {
        this.clientCommunication = clientCommunication;
    }

    public Integer getTimelyDeliveryB8() {
        return timelyDeliveryB8;
    }

    public void setTimelyDeliveryB8(Integer timelyDeliveryB8) {
        this.timelyDeliveryB8 = timelyDeliveryB8;
    }

    public Integer getCodeQualityB8() {
        return codeQualityB8;
    }

    public void setCodeQualityB8(Integer codeQualityB8) {
        this.codeQualityB8 = codeQualityB8;
    }

    public Integer getImprovement() {
        return improvement;
    }

    public void setImprovement(Integer improvement) {
        this.improvement = improvement;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }
}


