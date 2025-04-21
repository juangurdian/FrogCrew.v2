package com.tcu.frogcrew.controller;

import com.tcu.frogcrew.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/crew")
public class CrewController {

    @PostMapping("/availability")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> updateAvailability(
            @RequestBody Map<String, Object> availabilityData,
            Authentication authentication) {
        
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        
        // In a real application, we would save this to the database
        // For this demo, we'll just return success
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Availability updated successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/time-off")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> requestTimeOff(
            @RequestBody Map<String, Object> timeOffRequest,
            Authentication authentication) {
        
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        
        // In a real application, we would save the time-off request
        // For this demo, we'll just return success
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Time-off request submitted successfully");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/schedule")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<?> getSchedule(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof User)) {
            return ResponseEntity.status(401).build();
        }
        
        User user = (User) authentication.getPrincipal();
        
        // In a real application, we would fetch the user's schedule
        // For this demo, we'll return mock data
        
        return ResponseEntity.ok(getMockSchedule());
    }
    
    private Object getMockSchedule() {
        Map<String, Object> shift1 = new HashMap<>();
        shift1.put("id", 1);
        shift1.put("title", "Morning News");
        shift1.put("date", "2024-04-16");
        shift1.put("startTime", "06:00");
        shift1.put("endTime", "09:00");
        shift1.put("location", "Studio A");
        shift1.put("position", "Camera Operator");
        
        Map<String, Object> shift2 = new HashMap<>();
        shift2.put("id", 2);
        shift2.put("title", "Sports Coverage");
        shift2.put("date", "2024-04-18");
        shift2.put("startTime", "14:00");
        shift2.put("endTime", "17:00");
        shift2.put("location", "Football Stadium");
        shift2.put("position", "Audio Engineer");
        
        return java.util.Arrays.asList(shift1, shift2);
    }
} 