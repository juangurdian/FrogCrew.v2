package com.tcu.frogcrew.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invitations")
public class Invitation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String position;
    
    @Column(length = 1000)
    private String message;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.PENDING;
    
    @Column(name = "token", unique = true)
    private String token;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        expiresAt = LocalDateTime.now().plusDays(7); // Invitations expire after 7 days
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum InvitationStatus {
        PENDING,
        ACCEPTED,
        DECLINED,
        CANCELLED,
        EXPIRED
    }
} 