package com.tcu.frogcrew.controller;

import com.tcu.frogcrew.model.Invitation;
import com.tcu.frogcrew.service.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/single")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createSingleInvitation(@RequestBody Map<String, String> request) {
        try {
            String email = request.get("email");
            String position = request.get("position");
            String message = request.get("message");
            
            Invitation invitation = invitationService.createInvitation(email, position, message);
            return ResponseEntity.ok(invitation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/bulk")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBulkInvitations(@RequestParam("file") MultipartFile file) {
        try {
            List<Invitation> invitations = new ArrayList<>();
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        Invitation invitation = new Invitation();
                        invitation.setEmail(parts[0].trim());
                        invitation.setPosition(parts[1].trim());
                        if (parts.length > 2) {
                            invitation.setMessage(parts[2].trim());
                        }
                        invitations.add(invitation);
                    }
                }
            }
            
            List<Invitation> createdInvitations = invitationService.createBulkInvitations(invitations);
            return ResponseEntity.ok(createdInvitations);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{id}/resend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> resendInvitation(@PathVariable Long id) {
        try {
            Invitation invitation = invitationService.resendInvitation(id);
            return ResponseEntity.ok(invitation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> cancelInvitation(@PathVariable Long id) {
        try {
            invitationService.cancelInvitation(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/recent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRecentInvitations() {
        List<Invitation> invitations = invitationService.getRecentInvitations();
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<?> validateInvitation(@PathVariable String token) {
        try {
            Invitation invitation = invitationService.validateInvitation(token);
            return ResponseEntity.ok(invitation);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
} 