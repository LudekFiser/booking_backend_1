package org.example.booking_appointment.dto.auth.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendForgotPasswordRequest {

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter a valid email...")
    private String email;
}
