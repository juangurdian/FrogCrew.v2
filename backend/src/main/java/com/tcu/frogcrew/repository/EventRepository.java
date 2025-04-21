package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    List<Event> findByStartTimeAfterOrderByStartTimeAsc(LocalDateTime startTime);
    
    @Query("SELECT e FROM Event e WHERE e.startTime BETWEEN ?1 AND ?2 ORDER BY e.startTime ASC")
    List<Event> findEventsInRange(LocalDateTime start, LocalDateTime end);
} 