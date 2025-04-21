package com.tcu.frogcrew.controller;

import com.tcu.frogcrew.model.User;
import com.tcu.frogcrew.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/crew/dashboard")
public class CrewDashboardController {

    @Autowired
    private DashboardService dashboardService;
    
    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getCrewDashboard(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        Map<String, Object> dashboard = new HashMap<>();
        
        // For demonstration, using the same service, but in production
        // you would create crew-specific methods in the service
        dashboard.put("upcomingShifts", dashboardService.getUpcomingShifts());
        dashboard.put("notifications", getMockNotifications());
        dashboard.put("availableHours", 24); // Mock data
        
        return ResponseEntity.ok(dashboard);
    }
    
    @GetMapping("/upcoming-shifts")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> getUpcomingShifts(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(dashboardService.getUpcomingShifts());
    }
    
    @GetMapping("/notifications")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> getNotifications(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(getMockNotifications());
    }
    
    @GetMapping("/availability-hours")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> getAvailabilityHours(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(24); // Mock data
    }
    
    // Mock data for notifications
    private Object getMockNotifications() {
        Map<String, Object> notification1 = new HashMap<>();
        notification1.put("id", 1);
        notification1.put("title", "New Schedule Posted");
        notification1.put("message", "The schedule for next month has been posted.");
        notification1.put("time", "2 hours ago");
        notification1.put("type", "schedule");
        
        Map<String, Object> notification2 = new HashMap<>();
        notification2.put("id", 2);
        notification2.put("title", "Shift Exchange Request");
        notification2.put("message", "John Doe wants to exchange shifts with you.");
        notification2.put("time", "1 day ago");
        notification2.put("type", "request");
        
        return java.util.Arrays.asList(notification1, notification2);
    }
} 