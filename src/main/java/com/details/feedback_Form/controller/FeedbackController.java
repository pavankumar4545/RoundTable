package com.details.feedback_Form.controller;

import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.entity.Feedback;
import com.details.feedback_Form.repository.EmployeeRepository;
import com.details.feedback_Form.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
        try {
            String decodedEmail = URLDecoder.decode(email, StandardCharsets.UTF_8);
            List<Feedback> feedbackList = feedbackService.getFeedbackByEmail(decodedEmail);

            if (feedbackList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
            }
            return ResponseEntity.ok(feedbackList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        }
    }

    @PostMapping(value = "/submit", consumes = "multipart/form-data")
    public ResponseEntity<?> submitFeedback(
            @RequestParam("employeeId") Long employeeId,
            @RequestParam("month") String month,
            @RequestParam("year") int year,
            @RequestParam(value = "timelyDelivery", required = false) Integer timelyDelivery,
            @RequestParam(value = "codeQuality", required = false) Integer codeQuality,
            @RequestParam(value = "improvement", required = false) Integer improvement,
            @RequestParam(value = "comment", required = false) String comment,
            @RequestParam(value = "attachment", required = false) MultipartFile attachment) {

        try {
            String managerEmail = "pavankumarmadli48@gmail.com";
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

            Feedback feedback = new Feedback();
            feedback.setEmployee(employee);
            feedback.setManagerEmail(managerEmail);
            feedback.setMonth(month);
            feedback.setYear(year);
            feedback.setTimelyDeliveryB7(timelyDelivery);
            feedback.setCodeQualityB7(codeQuality);
            feedback.setImprovement(improvement);
            feedback.setComment(comment);
            feedback.setStatus("PENDING");

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
            return ResponseEntity.ok("{\"message\": \"Feedback submitted successfully!\"}");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to submit feedback\", \"details\": \"" + e.getMessage() + "\"}");
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
    public ResponseEntity<Boolean> feedbackExists(@RequestParam Long employeeId, @RequestParam String month, @RequestParam int year) {
        boolean exists = feedbackService.feedbackAlreadySubmitted(employeeId, month, year);
        return ResponseEntity.ok(exists);
    }
}
