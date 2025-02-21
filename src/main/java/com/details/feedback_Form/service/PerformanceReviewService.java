package com.details.feedback_Form.service;

import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.entity.PerformanceReview;
import com.details.feedback_Form.entity.RTCycle;
import com.details.feedback_Form.repository.EmployeeRepository;
import com.details.feedback_Form.repository.FeedbackRepository;
import com.details.feedback_Form.repository.PerformanceReviewRepository;
import com.details.feedback_Form.repository.RTCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.Optional;

@Service
public class PerformanceReviewService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private RTCycleRepository rtCycleRepository;

    @Autowired
    private PerformanceReviewRepository performanceReviewRepository;

    private int getMonthNumber(String monthName) {
        try {
            return Month.valueOf(monthName.toUpperCase()).getValue();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    public PerformanceReview getReviewData(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        RTCycle cycle = rtCycleRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("RT Cycle not found"));

        Optional<PerformanceReview> existingReview = performanceReviewRepository.findByEmployeeId(employeeId);
        if (existingReview.isPresent()) {
            System.out.println("Existing review found for Employee ID: " + employeeId);
            return existingReview.get();
        }

        int feedbackCount = employee.getFeedbackCount();

        int totalMonths = 6;
        double submissionPercentage = ((double) feedbackCount / totalMonths) * 100;
        boolean canSubmit = submissionPercentage >= 75;

        int performanceRating = (int) submissionPercentage;

        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setName(employee.getName());
        review.setStartMonth(cycle.getStartMonth());
        review.setStartYear(cycle.getStartYear());
        review.setEndMonth(cycle.getEndMonth());
        review.setEndYear(cycle.getEndYear());
        review.setPerformanceRating(performanceRating);
        review.setSubmitEnabled(canSubmit);

        PerformanceReview savedReview = performanceReviewRepository.save(review);

        System.out.println("Feedback Submitted: " + feedbackCount);
        System.out.println("Submission Percentage: " + submissionPercentage + "%");
        System.out.println("Performance Rating: " + performanceRating);
        System.out.println("Generated Review ID: " + savedReview.getId());

        return savedReview;  // Ensure the ID is not null
    }



    public PerformanceReview saveReview(PerformanceReview review) {
        return performanceReviewRepository.save(review);
    }
}
