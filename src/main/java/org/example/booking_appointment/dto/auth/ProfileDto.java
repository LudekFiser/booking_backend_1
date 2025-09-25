package org.example.booking_appointment.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProfileDto {
    private boolean isVerified;
    private boolean twoFactorEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

