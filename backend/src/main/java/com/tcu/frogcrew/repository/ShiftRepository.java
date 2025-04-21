package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    
    List<Shift> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime startTime);
    
    @Query("SELECT s FROM Shift s WHERE s.startTime BETWEEN ?1 AND ?2 ORDER BY s.startTime ASC")
    List<Shift> findShiftsInRange(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT COUNT(s) FROM Shift s WHERE s.startTime > CURRENT_TIMESTAMP")
    int countUpcomingShifts();
    
    @Query("SELECT s FROM Shift s WHERE s.startTime > CURRENT_TIMESTAMP ORDER BY s.startTime ASC")
    List<Shift> findUpcomingShifts();
} 