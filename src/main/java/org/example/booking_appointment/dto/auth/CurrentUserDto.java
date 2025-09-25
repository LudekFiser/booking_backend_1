package org.example.booking_appointment.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.booking_appointment.enums.ROLE;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class CurrentUserDto {
    private Long id;
    private String name;
    private String email;
    private ROLE role;

    private String phoneNumber;
    private LocalDate dateOfBirth;

    private ProfileDto profile;
}
