package org.example.booking_appointment.dto.auth;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.enums.ROLE;

import java.time.LocalDate;


@Data
@Builder
public class RegisterResponse {

    private Long id;
    private String email;
    private String name;
    private ROLE role;

    private String phoneNumber;
    private LocalDate dateOfBirth;

    private ProfileDto profile;
}
