package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.ShiftAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftAssignmentRepository extends JpaRepository<ShiftAssignment, Long> {
    
    List<ShiftAssignment> findByShiftId(Long shiftId);
    
    List<ShiftAssignment> findByUserIdOrderByShiftStartTimeDesc(Long userId);
    
    @Query("SELECT sa FROM ShiftAssignment sa WHERE sa.status = 'PENDING' ORDER BY sa.createdAt DESC")
    List<ShiftAssignment> findPendingAssignments();
    
    @Query("SELECT COUNT(sa) FROM ShiftAssignment sa WHERE sa.status = 'PENDING'")
    int countPendingAssignments();
} 