package com.details.feedback_Form.controller;

import com.details.feedback_Form.entity.PerformanceReview;
import com.details.feedback_Form.service.PerformanceReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class PerformanceReviewController {

    @Autowired
    private PerformanceReviewService reviewService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<PerformanceReview> getReviewData(@PathVariable Long employeeId) {
        PerformanceReview review = reviewService.getReviewData(employeeId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/submit")
    public ResponseEntity<PerformanceReview> submitReview(@RequestBody PerformanceReview review) {
        if (!review.isSubmitEnabled()) {
            return ResponseEntity.badRequest().body(null);
        }
        PerformanceReview savedReview = reviewService.saveReview(review);
        return ResponseEntity.ok(savedReview);
    }
}
