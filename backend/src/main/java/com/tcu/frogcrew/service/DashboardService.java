package com.tcu.frogcrew.service;

import com.tcu.frogcrew.model.*;
import com.tcu.frogcrew.repository.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ShiftRepository shiftRepository;
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private PositionRepository positionRepository;
    
    @Autowired
    private ShiftAssignmentRepository shiftAssignmentRepository;
    
    @Autowired
    private UserPositionRepository userPositionRepository;
    
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        
        // Total number of crew members (excluding admins)
        long totalCrewMembers = userRepository.count();
        
        // Number of upcoming shifts
        int upcomingShifts = shiftRepository.countUpcomingShifts();
        
        // Number of pending requests
        int pendingRequests = shiftAssignmentRepository.countPendingAssignments();
        
        // Number of available crew (simplified for demo)
        int availableCrew = (int) (totalCrewMembers * 0.75); // Assuming 75% of crew is available
        
        summary.put("totalCrewMembers", totalCrewMembers);
        summary.put("upcomingShifts", upcomingShifts);
        summary.put("pendingRequests", pendingRequests);
        summary.put("availableCrew", availableCrew);
        
        return summary;
    }
    
    public List<Map<String, Object>> getUpcomingShifts() {
        return shiftRepository.findUpcomingShifts().stream()
                .limit(5)
                .map(this::convertShiftToMap)
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getRecentActivities() {
        return activityRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(this::convertActivityToMap)
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getCrewPositionCounts() {
        List<Position> positions = positionRepository.findAll();
        List<Object[]> counts = userPositionRepository.countUsersByPosition();
        
        Map<Long, Long> countMap = new HashMap<>();
        for (Object[] count : counts) {
            countMap.put((Long) count[0], (Long) count[1]);
        }
        
        return positions.stream()
                .map(position -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("name", position.getName());
                    result.put("count", countMap.getOrDefault(position.getId(), 0L));
                    result.put("color", position.getColor());
                    return result;
                })
                .collect(Collectors.toList());
    }
    
    public List<Map<String, Object>> getShiftCoverage() {
        return shiftRepository.findUpcomingShifts().stream()
                .limit(4)
                .map(shift -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("id", shift.getId());
                    result.put("title", shift.getTitle());
                    result.put("time", formatShiftTime(shift));
                    
                    int coverage = shift.getCoveragePercentage();
                    result.put("coverage", coverage);
                    
                    // Determine coverage class based on percentage
                    String coverageClass;
                    if (coverage >= 90) {
                        coverageClass = "bg-green-500";
                    } else if (coverage >= 70) {
                        coverageClass = "bg-yellow-500";
                    } else {
                        coverageClass = "bg-red-500";
                    }
                    result.put("coverageClass", coverageClass);
                    
                    return result;
                })
                .collect(Collectors.toList());
    }
    
    public List<CalendarDay> getCalendarDays() {
        // This is a simplified implementation
        List<CalendarDay> days = new java.util.ArrayList<>();
        LocalDateTime today = LocalDateTime.now();
        int daysInMonth = today.getMonth().length(today.toLocalDate().isLeapYear());
        
        for (int i = 1; i <= 35; i++) {
            boolean isInRange = i <= daysInMonth;
            boolean isToday = i == today.getDayOfMonth();
            
            // Randomly assign shifts to some days for demo purposes
            boolean hasShifts = isInRange && (i % 5 == 0 || i % 7 == 0);
            
            days.add(new CalendarDay(
                isInRange ? i : 0,
                isToday,
                hasShifts
            ));
        }
        
        return days;
    }
    
    private Map<String, Object> convertShiftToMap(Shift shift) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", shift.getId());
        result.put("title", shift.getTitle());
        result.put("date", shift.getStartTime().toLocalDate().toString());
        result.put("time", formatShiftTime(shift));
        
        boolean isFullyStaffed = shift.isFullyStaffed();
        result.put("status", isFullyStaffed ? "Fully Staffed" : "Needs Staff");
        result.put("statusClass", isFullyStaffed ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800");
        
        return result;
    }
    
    private String formatShiftTime(Shift shift) {
        return String.format("%02d:%02d - %02d:%02d", 
            shift.getStartTime().getHour(),
            shift.getStartTime().getMinute(),
            shift.getEndTime().getHour(),
            shift.getEndTime().getMinute()
        );
    }
    
    private Map<String, Object> convertActivityToMap(Activity activity) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", activity.getId());
        result.put("description", activity.getDescription());
        result.put("time", getTimeAgo(activity.getCreatedAt()));
        
        // Set icon properties based on activity type
        switch (activity.getActivityType()) {
            case AVAILABILITY_UPDATE:
                result.put("iconPath", "M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2");
                result.put("iconBg", "bg-blue-100");
                result.put("iconColor", "text-blue-600");
                break;
            case SHIFT_CREATION:
                result.put("iconPath", "M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z");
                result.put("iconBg", "bg-green-100");
                result.put("iconColor", "text-green-600");
                break;
            case TIME_OFF_REQUEST:
                result.put("iconPath", "M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z");
                result.put("iconBg", "bg-yellow-100");
                result.put("iconColor", "text-yellow-600");
                break;
            default:
                result.put("iconPath", "M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z");
                result.put("iconBg", "bg-gray-100");
                result.put("iconColor", "text-gray-600");
        }
        
        return result;
    }
    
    private String getTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = java.time.Duration.between(dateTime, now).toMinutes();
        
        if (minutes < 60) {
            return minutes + " minutes ago";
        } else if (minutes < 24 * 60) {
            return (minutes / 60) + " hours ago";
        } else {
            return (minutes / (24 * 60)) + " days ago";
        }
    }
    
    @Data
    public static class CalendarDay {
        private final int date;
        private final boolean isToday;
        private final boolean hasShifts;
    }
} 