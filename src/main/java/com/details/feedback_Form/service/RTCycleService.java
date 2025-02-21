package com.details.feedback_Form.service;

import com.details.feedback_Form.entity.RTCycle;
import java.util.List;

public interface RTCycleService {
    RTCycle createRTCycle(Long adminId, RTCycle rtCycle);
    RTCycle getCurrentRTCycle(Long adminId);
    RTCycle getRTCycleById(Long id);
    RTCycle updateRTCycle(Long adminId, Long id, RTCycle updatedRTCycle);
    List<RTCycle> getAllRTCycles(Long adminId);
    void deleteAll();
}
