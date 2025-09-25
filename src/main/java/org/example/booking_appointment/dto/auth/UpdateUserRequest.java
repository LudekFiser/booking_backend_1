package org.example.booking_appointment.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.booking_appointment.validation.Lowercase;

@Data
public class UpdateUserRequest {

    @NotBlank(message = "First Name is required!")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String firstName;

    @NotBlank(message = "Last Name is required!")
    @Size(max = 255, message = "Name must be less than 255 characters")
    private String lastName;

    @NotBlank(message = "Email is required!")
    @Email(message = "Enter a valid email...")
    @Lowercase(message = "Email must be in lowercase")
    private String email;


    private Boolean twoFactorEmail;


}
