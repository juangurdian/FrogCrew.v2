package com.tcu.frogcrew.repository;

import com.tcu.frogcrew.model.Invitation;
import com.tcu.frogcrew.model.Invitation.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByEmailOrderByCreatedAtDesc(String email);
    List<Invitation> findByStatusOrderByCreatedAtDesc(InvitationStatus status);
    Optional<Invitation> findByToken(String token);
    boolean existsByEmailAndStatus(String email, InvitationStatus status);
    
    @Query("SELECT i FROM Invitation i WHERE i.status = :status AND i.expiresAt < :now")
    List<Invitation> findExpiredInvitations(@Param("status") InvitationStatus status, @Param("now") LocalDateTime now);
    
    List<Invitation> findTop10ByOrderByCreatedAtDesc();
} 