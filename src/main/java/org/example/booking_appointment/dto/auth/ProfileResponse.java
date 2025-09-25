package org.example.booking_appointment.dto.auth;

import lombok.Builder;
import lombok.Data;
import org.example.booking_appointment.dto.hotel.HotelOwnerResponse;
import org.example.booking_appointment.dto.image.ImageDto;
import org.example.booking_appointment.enums.ROLE;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class ProfileResponse {

    private Long id;
    private String name;
    private String email;
    private ROLE role;
    private Boolean verified;
    private Boolean twoFactorEmail;
    private LocalDate dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ImageDto avatar;

    private HotelOwnerResponse artist;
}
