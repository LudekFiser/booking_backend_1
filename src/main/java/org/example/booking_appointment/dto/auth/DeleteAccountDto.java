package org.example.booking_appointment.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteAccountDto {

    @NotBlank(message = "otp is required!")
    private String otp;
}
