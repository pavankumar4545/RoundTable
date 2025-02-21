package com.details.feedback_Form.controller;

import com.details.feedback_Form.entity.RTCycle;
import com.details.feedback_Form.service.RTCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rt-cycle")
public class RTCycleController {

    @Autowired
    private RTCycleService rtCycleService;

    @PostMapping("/create")
    public ResponseEntity<?> createRTCycle(@RequestHeader("adminId") Long adminId,
                                           @RequestBody RTCycle rtCycle) {
        try {
            RTCycle createdCycle = rtCycleService.createRTCycle(adminId, rtCycle);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCycle);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentRTCycle(@RequestHeader("adminId") Long adminId) {
        try {
            RTCycle currentCycle = rtCycleService.getCurrentRTCycle(adminId);
            return ResponseEntity.ok(currentCycle);
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRTCycle(@RequestHeader("adminId") Long adminId,
                                           @PathVariable Long id,
                                           @RequestBody RTCycle updatedCycle) {
        try {
            RTCycle rtCycle = rtCycleService.updateRTCycle(adminId, id, updatedCycle);
            return ResponseEntity.ok(rtCycle);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RTCycle>> getAllRTCycles(@RequestHeader("adminId") Long adminId) {
        return ResponseEntity.ok(rtCycleService.getAllRTCycles(adminId));
    }
}
