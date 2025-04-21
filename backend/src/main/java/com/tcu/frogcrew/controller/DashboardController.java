package com.tcu.frogcrew.controller;

import com.tcu.frogcrew.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        dashboard.put("summary", dashboardService.getDashboardSummary());
        dashboard.put("crewPositions", dashboardService.getCrewPositionCounts());
        dashboard.put("shiftCoverage", dashboardService.getShiftCoverage());
        dashboard.put("recentActivities", dashboardService.getRecentActivities());
        dashboard.put("upcomingShifts", dashboardService.getUpcomingShifts());
        dashboard.put("calendarDays", dashboardService.getCalendarDays());
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getDashboardSummary() {
        return ResponseEntity.ok(dashboardService.getDashboardSummary());
    }
    
    @GetMapping("/crew-positions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCrewPositions() {
        return ResponseEntity.ok(dashboardService.getCrewPositionCounts());
    }
    
    @GetMapping("/shift-coverage")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getShiftCoverage() {
        return ResponseEntity.ok(dashboardService.getShiftCoverage());
    }
    
    @GetMapping("/recent-activities")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRecentActivities() {
        return ResponseEntity.ok(dashboardService.getRecentActivities());
    }
    
    @GetMapping("/upcoming-shifts")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUpcomingShifts() {
        return ResponseEntity.ok(dashboardService.getUpcomingShifts());
    }
    
    @GetMapping("/calendar-days")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCalendarDays() {
        return ResponseEntity.ok(dashboardService.getCalendarDays());
    }
} 