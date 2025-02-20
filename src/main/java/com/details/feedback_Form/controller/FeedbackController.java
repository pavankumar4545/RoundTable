package com.details.feedback_Form.controller;

import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.entity.Feedback;
import com.details.feedback_Form.repository.EmployeeRepository;
import com.details.feedback_Form.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EmployeeRepository employeeRepository;


    @GetMapping("/last-six-months/{employeeId}")
    public ResponseEntity<List<Feedback>> getLastSixMonthsFeedback(@PathVariable Long employeeId) {
        List<Feedback> feedbackList = feedbackService.getLastSixMonthsFeedback(employeeId);
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/by-email/{email}")
    public ResponseEntity<List<Feedback>> getFeedbackByEmail(@PathVariable String email) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByEmail(email);
        return ResponseEntity.ok(feedbackList);
    }


    @PostMapping("/submit")
    public ResponseEntity<String> submitFeedback(
            @RequestParam Long employeeId,
            @RequestParam String managerEmail,
            @RequestParam String month,
            @RequestParam int year,
            @RequestParam(required = false) Integer leadership,
            @RequestParam(required = false) Integer orgContribution,
            @RequestParam(required = false) Integer assistingInPreSales,
            @RequestParam(required = false) Integer timelyDeliveryB7,
            @RequestParam(required = false) Integer codeQualityB7,
            @RequestParam(required = false) Integer clientCommunication,
            @RequestParam(required = false) Integer timelyDeliveryB8,
            @RequestParam(required = false) Integer codeQualityB8,
            @RequestParam(required = false) Integer improvement,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) MultipartFile attachment) {

        try {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            String bandLevel = employee.getBandLevel();  // Fetch band level

            Feedback feedback = new Feedback();
            feedback.setEmployee(employee);
            feedback.setManagerEmail(managerEmail);
            feedback.setMonth(month);
            feedback.setYear(year);
            feedback.setComment(comment);
            feedback.setStatus("PENDING");

            // Assign fields based on Band Level
            if (bandLevel.equals("B6")) {
                feedback.setLeadership(leadership);
                feedback.setOrgContribution(orgContribution);
                feedback.setAssistingInPreSales(assistingInPreSales);
            } else if (bandLevel.equals("B7")) {
                feedback.setTimelyDeliveryB7(timelyDeliveryB7);
                feedback.setCodeQualityB7(codeQualityB7);
                feedback.setClientCommunication(clientCommunication);
            } else if (bandLevel.equals("B8")) {
                feedback.setTimelyDeliveryB8(timelyDeliveryB8);
                feedback.setCodeQualityB8(codeQualityB8);
                feedback.setImprovement(improvement);
            }

            // File Upload Logic
            if (attachment != null && !attachment.isEmpty()) {
                String uploadDir = "uploads/";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String filePath = uploadDir + attachment.getOriginalFilename();
                Files.write(Paths.get(filePath), attachment.getBytes());
                feedback.setAttachmentUrl(filePath);
            }

            feedbackService.submitFeedback(feedback);
            return ResponseEntity.ok("Feedback submitted successfully!");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to submit feedback.");
        }
    }
    @GetMapping("/{feedbackId}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long feedbackId) {
        Feedback feedback = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(feedback);
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<Feedback>> getEmployeeFeedback(@PathVariable Long employeeId) {
        List<Feedback> feedbackList = feedbackService.getEmployeeFeedback(employeeId);
        return ResponseEntity.ok(feedbackList);
    }
    @PostMapping("/approve/{feedbackId}")
    public ResponseEntity<String> approveFeedback(@PathVariable Long feedbackId) {
        boolean approved = feedbackService.approveFeedback(feedbackId);
        return approved ? ResponseEntity.ok("Feedback approved!") : ResponseEntity.badRequest().body("Approval failed.");
    }
    @PostMapping("/reject/{feedbackId}")
    public ResponseEntity<String> rejectFeedback(@PathVariable Long feedbackId, @RequestParam String reason) {
        boolean rejected = feedbackService.rejectFeedback(feedbackId, reason);
        return rejected ? ResponseEntity.ok("Feedback rejected!") : ResponseEntity.badRequest().body("Rejection failed.");
    }
    @GetMapping("/exists")
    public ResponseEntity<Boolean> feedbackExists(
            @RequestParam Long employeeId, @RequestParam String month, @RequestParam int year) {
        boolean exists = feedbackService.feedbackAlreadySubmitted(employeeId, month, year);
        return ResponseEntity.ok(exists);
    }

}
