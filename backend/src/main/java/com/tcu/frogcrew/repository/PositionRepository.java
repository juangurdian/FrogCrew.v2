package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    Optional<Position> findByName(String name);
    
    boolean existsByName(String name);
    
    @Query("SELECT p.name, COUNT(up.id) FROM Position p LEFT JOIN UserPosition up ON p.id = up.position.id GROUP BY p.id, p.name ORDER BY p.name")
    List<Object[]> countUsersByPosition();
} 