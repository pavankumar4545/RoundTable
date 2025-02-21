package com.details.feedback_Form.service;

import com.details.feedback_Form.entity.Employee;
import com.details.feedback_Form.entity.Feedback;
import com.details.feedback_Form.entity.PerformanceReview;
import com.details.feedback_Form.entity.RTCycle;
import com.details.feedback_Form.repository.EmployeeRepository;
import com.details.feedback_Form.repository.FeedbackRepository;
import com.details.feedback_Form.repository.PerformanceReviewRepository;
import com.details.feedback_Form.repository.RTCycleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
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

    // Convert month name to month number
    private int getMonthNumber(String monthName) {
        try {
            return Month.valueOf(monthName.toUpperCase()).getValue();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

    // Fetch review data with updated logic
    // Fetch review data with updated logic
    public PerformanceReview getReviewData(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        RTCycle cycle = rtCycleRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("RT Cycle not found"));

        // ⃣ Get feedback count directly from the Employee table
        int feedbackCount = employee.getFeedbackCount();

        //  Calculate submission percentage based on 6 months
        int totalMonths = 6;
        double submissionPercentage = ((double) feedbackCount / totalMonths) * 100;
        boolean canSubmit = submissionPercentage >= 75;

        // Set Performance Rating as Submission Percentage
        int performanceRating = (int) submissionPercentage;

        // Create Performance Review
        PerformanceReview review = new PerformanceReview();
        review.setEmployee(employee);
        review.setName(employee.getName());
        review.setStartMonth(cycle.getStartMonth());
        review.setStartYear(cycle.getStartYear());
        review.setEndMonth(cycle.getEndMonth());
        review.setEndYear(cycle.getEndYear());
        review.setPerformanceRating(performanceRating);  // 🔑 Set submission percentage
        review.setSubmitEnabled(canSubmit);

        //  Logs for Debugging
        System.out.println("Feedback Submitted: " + feedbackCount);
        System.out.println("Submission Percentage: " + submissionPercentage + "%");
        System.out.println("Performance Rating: " + performanceRating);

        return review;
    }


    // Save review
    public PerformanceReview saveReview(PerformanceReview review) {
        return performanceReviewRepository.save(review);
    }
}

