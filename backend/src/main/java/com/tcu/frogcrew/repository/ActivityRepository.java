package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    List<Activity> findTop10ByOrderByCreatedAtDesc();
    
    List<Activity> findByUserIdOrderByCreatedAtDesc(Long userId);
} 