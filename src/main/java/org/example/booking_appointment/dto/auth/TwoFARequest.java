package org.example.booking_appointment.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TwoFARequest {

    @NotBlank(message = "OTP is required")
    private String otp;
}
