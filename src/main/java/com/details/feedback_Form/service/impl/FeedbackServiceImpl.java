package com.details.feedback_Form.service.impl;

import com.details.feedback_Form.entity.Feedback;
import com.details.feedback_Form.repository.FeedbackRepository;
import com.details.feedback_Form.service.EmailService;
import com.details.feedback_Form.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public Feedback submitFeedback(Feedback feedback) {
        feedback.setStatus("PENDING");
        Feedback savedFeedback = feedbackRepository.save(feedback);

        // Send email notification
        sendEmailNotification(savedFeedback);

        return savedFeedback;
    }

    @Override
    @Transactional
    public boolean approveFeedback(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setStatus("APPROVED");
        feedbackRepository.save(feedback);

        // Send approval email
        String subject = "Feedback Approved ✅";
        String content = "Your feedback for " + feedback.getMonth() + " " + feedback.getYear() + " has been approved!";
        emailService.sendFeedbackEmail(feedback.getEmployee().getEmail(), "hr@company.com", subject, content);

        return true;
    }

    @Override
    @Transactional
    public boolean rejectFeedback(Long feedbackId, String reason) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        feedback.setStatus("REJECTED");
        feedbackRepository.save(feedback);

        // Send rejection email
        String subject = "Feedback Rejected ❌";
        String content = "Your feedback for " + feedback.getMonth() + " " + feedback.getYear() + " was rejected.\nReason: " + reason;
        emailService.sendFeedbackEmail(feedback.getEmployee().getEmail(), "hr@company.com", subject, content);

        return true;
    }

    @Override
    public List<Feedback> getEmployeeFeedback(Long employeeId) {
        return feedbackRepository.findByEmployeeId(employeeId);
    }

    @Override
    public Feedback getFeedbackById(Long feedbackId) {
        return feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    @Override
    public List<Feedback> getLastSixMonthsFeedback(Long employeeId) {
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        return feedbackRepository.findFeedbackForLastSixMonths(employeeId, sixMonthsAgo);
    }

    @Override
    public List<Feedback> getFeedbackByEmail(String email) {
        return feedbackRepository.findByEmployeeEmail(email);
    }

    @Override
    public boolean feedbackAlreadySubmitted(Long employeeId, String month, int year) {
        return feedbackRepository.existsByEmployeeIdAndMonthAndYear(employeeId, month, year);
    }

    private void sendEmailNotification(Feedback feedback) {
        String subject = "New Feedback Submitted";
        String content = "<p>New feedback has been submitted for approval.</p>" +
                "<p><b>Employee:</b> " + feedback.getEmployee().getName() + "</p>" +
                "<p><b>Month:</b> " + feedback.getMonth() + " " + feedback.getYear() + "</p>" +
                "<p>Please review and take action.</p>";

        emailService.sendFeedbackEmail(feedback.getManagerEmail(), "hr@company.com", subject, content);
    }
}