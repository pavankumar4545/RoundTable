package com.details.feedback_Form.repository;
import com.details.feedback_Form.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.employee.id = :employeeId")
    List<Feedback> findByEmployeeId(@Param("employeeId") Long employeeId);


    List<Feedback> findByManagerEmail(String managerEmail);

    @Query("SELECT f FROM Feedback f WHERE f.employee.id = :employeeId AND f.submissionDate >= :sixMonthsAgo")
    List<Feedback> findFeedbackForLastSixMonths(@Param("employeeId") Long employeeId, @Param("sixMonthsAgo") LocalDate sixMonthsAgo);

    @Query("SELECT f FROM Feedback f WHERE f.employee.email = :email")
    List<Feedback> findByEmployeeEmail(@Param("email") String email);

    boolean existsByEmployeeIdAndMonthAndYear(Long employeeId, String month, int year);

    @Query("SELECT COUNT(f) FROM Feedback f WHERE f.employee.id = :employeeId")
    int countByEmployeeId(@Param("employeeId") Long employeeId);



//    @Query("SELECT COALESCE(AVG((f.param1 + f.param2 + f.param3) * 1.0 / 3), 0) FROM Feedback f WHERE f.employee.id = :employeeId")
//    Double calculateAverageRating(@Param("employeeId") Long employeeId);

}
