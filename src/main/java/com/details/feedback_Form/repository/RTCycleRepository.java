package com.details.feedback_Form.repository;

import com.details.feedback_Form.entity.RTCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RTCycleRepository extends JpaRepository<RTCycle, Long> {

    RTCycle findTopByOrderByIdDesc();  // Get the most recent RT Cycle

    @Query("SELECT COUNT(r) > 0 FROM RTCycle r WHERE " +
            "(:startYear < r.endYear OR (:startYear = r.endYear AND :startMonth <= r.endMonth)) " +
            "AND (:endYear > r.startYear OR (:endYear = r.startYear AND :endMonth >= r.startMonth))")
    boolean existsOverlappingRTCycle(@Param("startMonth") int startMonth,
                                     @Param("startYear") int startYear,
                                     @Param("endMonth") int endMonth,
                                     @Param("endYear") int endYear);

}
