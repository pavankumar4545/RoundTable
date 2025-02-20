package com.details.feedback_Form.service;

import com.details.feedback_Form.entity.Feedback;
import java.util.List;

public interface FeedbackService {
    Feedback submitFeedback(Feedback feedback);
    boolean approveFeedback(Long feedbackId);
    boolean rejectFeedback(Long feedbackId, String reason);
    List<Feedback> getEmployeeFeedback(Long employeeId);
    Feedback getFeedbackById(Long feedbackId);
    List<Feedback> getLastSixMonthsFeedback(Long employeeId);
    List<Feedback> getFeedbackByEmail(String email);
    boolean feedbackAlreadySubmitted(Long employeeId, String month, int year);

}
