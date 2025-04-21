package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPositionRepository extends JpaRepository<UserPosition, Long> {
    
    List<UserPosition> findByUserId(Long userId);
    
    List<UserPosition> findByPositionId(Long positionId);
    
    @Query("SELECT up.position.id, COUNT(up.id) FROM UserPosition up GROUP BY up.position.id")
    List<Object[]> countUsersByPosition();
} 