package com.tcu.frogcrew.service;

import com.tcu.frogcrew.model.Invitation;
import com.tcu.frogcrew.model.Invitation.InvitationStatus;
import com.tcu.frogcrew.repository.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;
    
    @Autowired
    private MailService mailService;
    
    @Transactional
    public Invitation createInvitation(String email, String position, String message) {
        // Check if there's already a pending invitation
        if (invitationRepository.existsByEmailAndStatus(email, InvitationStatus.PENDING)) {
            throw new RuntimeException("A pending invitation already exists for this email");
        }
        
        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setPosition(position);
        invitation.setMessage(message);
        invitation.setToken(UUID.randomUUID().toString());
        
        Invitation savedInvitation = invitationRepository.save(invitation);

        // Send invitation email
        String subject = "You're Invited to Join FrogCrew!";
        String inviteLink = "http://localhost:3001/invite/" + invitation.getToken();
        String body = "Hello,\n\n" +
                "You have been invited to join FrogCrew as a " + position + ".\n" +
                (message != null && !message.isEmpty() ? ("Message: " + message + "\n\n") : "") +
                "Please click the link below to accept your invitation:\n" +
                inviteLink + "\n\n" +
                "If you did not expect this invitation, you can ignore this email.";
        mailService.sendEmail(email, subject, body);

        return savedInvitation;
    }
    
    @Transactional
    public List<Invitation> createBulkInvitations(List<Invitation> invitations) {
        // Process each invitation
        for (Invitation invitation : invitations) {
            if (invitationRepository.existsByEmailAndStatus(invitation.getEmail(), InvitationStatus.PENDING)) {
                throw new RuntimeException("A pending invitation already exists for email: " + invitation.getEmail());
            }
            invitation.setToken(UUID.randomUUID().toString());
        }
        
        return invitationRepository.saveAll(invitations);
    }
    
    @Transactional
    public Invitation resendInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
        
        // Only cancelled or expired invitations can be resent
        if (invitation.getStatus() != InvitationStatus.CANCELLED && 
            invitation.getStatus() != InvitationStatus.EXPIRED) {
            throw new RuntimeException("Only cancelled or expired invitations can be resent");
        }
        
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setToken(UUID.randomUUID().toString());
        invitation.setExpiresAt(LocalDateTime.now().plusDays(7));
        
        return invitationRepository.save(invitation);
    }
    
    @Transactional
    public void cancelInvitation(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
        
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new RuntimeException("Only pending invitations can be cancelled");
        }
        
        invitation.setStatus(InvitationStatus.CANCELLED);
        invitationRepository.save(invitation);
    }
    
    @Transactional(readOnly = true)
    public List<Invitation> getRecentInvitations() {
        return invitationRepository.findTop10ByOrderByCreatedAtDesc();
    }
    
    @Transactional
    public void processExpiredInvitations() {
        List<Invitation> expiredInvitations = invitationRepository.findExpiredInvitations(InvitationStatus.PENDING, LocalDateTime.now());
        for (Invitation invitation : expiredInvitations) {
            invitation.setStatus(InvitationStatus.EXPIRED);
        }
        invitationRepository.saveAll(expiredInvitations);
    }
    
    @Transactional(readOnly = true)
    public Invitation validateInvitation(String token) {
        return invitationRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid or expired invitation token"));
    }
} 