package com.details.feedback_Form.repository;

import com.details.feedback_Form.entity.RTCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RTCycleRepository extends JpaRepository<RTCycle, Long> {

    RTCycle findTopByOrderByIdDesc();  // Get the most recent RT Cycle

    @Query("SELECT COUNT(r) > 0 FROM RTCycle r WHERE " +
            "(:startYear BETWEEN r.startYear AND r.endYear) " +
            "AND (:startMonth BETWEEN r.startMonth AND r.endMonth)")
    boolean existsOverlappingRTCycle(@Param("startMonth") int startMonth,
                                     @Param("startYear") int startYear);
}
