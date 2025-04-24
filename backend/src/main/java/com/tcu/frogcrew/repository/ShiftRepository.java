package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    
    List<Shift> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime startTime);
    
    @Query("SELECT s FROM Shift s WHERE s.startTime BETWEEN ?1 AND ?2 ORDER BY s.startTime ASC")
    List<Shift> findShiftsInRange(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(s) FROM Shift s WHERE s.startTime > :now")
    int countUpcomingShifts(@Param("now") LocalDateTime now);
    
    @Query("SELECT s FROM Shift s WHERE s.startTime > :now ORDER BY s.startTime ASC")
    List<Shift> findUpcomingShifts(@Param("now") LocalDateTime now);
    
    @Query("SELECT s FROM Shift s WHERE DATE(s.startTime) = CURRENT_DATE ORDER BY s.startTime ASC")
    List<Shift> findTodayShifts();
    
    @Query("SELECT s FROM Shift s WHERE s.startTime >= :date AND s.startTime < :endDate ORDER BY s.startTime ASC")
    List<Shift> findShiftsByDate(@Param("date") LocalDateTime date, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(s) FROM Shift s WHERE s.startTime >= CURRENT_DATE AND s.startTime < CURRENT_DATE + 7")
    int countUpcomingWeekShifts();
} 