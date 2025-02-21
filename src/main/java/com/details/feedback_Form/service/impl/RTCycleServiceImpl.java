package com.details.feedback_Form.service.impl;

import com.details.feedback_Form.entity.RTCycle;
import com.details.feedback_Form.entity.Admin;
import com.details.feedback_Form.repository.RTCycleRepository;
import com.details.feedback_Form.repository.AdminRepository;
import com.details.feedback_Form.service.RTCycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RTCycleServiceImpl implements RTCycleService {

    @Autowired
    private RTCycleRepository rtCycleRepository;

    @Autowired
    private AdminRepository adminRepository;

    private void validateAdminAccess(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Unauthorized: Admin not found.");
        }
        if (!admin.getRole().equalsIgnoreCase("ADMIN") &&
                !admin.getRole().equalsIgnoreCase("HR") &&
                !admin.getRole().equalsIgnoreCase("MANAGER")) {
            throw new SecurityException("Access Denied: Only ADMIN, HR, or MANAGER can access this.");
        }
    }

    @Override
    public RTCycle createRTCycle(Long adminId, RTCycle rtCycle) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        validateAdminAccess(admin);

        // Ensure proper parameters are passed to check for overlapping RT cycles
        boolean exists = rtCycleRepository.existsOverlappingRTCycle(
                rtCycle.getStartMonth(), rtCycle.getStartYear(),
                rtCycle.getEndMonth(), rtCycle.getEndYear()
        );

        if (exists) {
            throw new IllegalArgumentException("An RT Cycle already exists for this time period.");
        }

        return rtCycleRepository.save(rtCycle);
    }


    @Override
    public RTCycle getCurrentRTCycle(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        validateAdminAccess(admin);
        return rtCycleRepository.findTopByOrderByIdDesc();
    }

    @Override
    public RTCycle getRTCycleById(Long id) {
        return rtCycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("RT Cycle not found."));
    }

    @Override
    public RTCycle updateRTCycle(Long adminId, Long id, RTCycle updatedRTCycle) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        validateAdminAccess(admin);

        RTCycle existingCycle = getRTCycleById(id);
        existingCycle.setStartMonth(updatedRTCycle.getStartMonth());
        existingCycle.setStartYear(updatedRTCycle.getStartYear());
        existingCycle.setEndMonth(updatedRTCycle.getEndMonth());
        existingCycle.setEndYear(updatedRTCycle.getEndYear());

        return rtCycleRepository.save(existingCycle);
    }

    @Override
    public List<RTCycle> getAllRTCycles(Long adminId) {
        Admin admin = adminRepository.findById(adminId).orElse(null);
        validateAdminAccess(admin);
        return rtCycleRepository.findAll();
    }

    @Override
    public void deleteAll() {
        rtCycleRepository.deleteAll();
    }
}
