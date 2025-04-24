package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.UserRole;
import com.tcu.frogcrew.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByRole(UserRole role);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.isActive = true AND u.role = com.tcu.frogcrew.model.UserRole.USER")
    int countAvailableUsers();
} 