package org.example.booking_appointment.mapper;


import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.auth.ProfileResponse;
import org.example.booking_appointment.dto.auth.RegisterRequest;

import org.example.booking_appointment.dto.auth.SearchProfileResponse;
import org.example.booking_appointment.entity.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProfileMapper {

    private final ImageMapper imageMapper;
    private final HotelOwnerMapper artistMapper;
    private final HotelOwnerMapper hotelOwnerMapper;

    public Profile toEntity(RegisterRequest req) {

        return Profile.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(req.getPassword())
                .dateOfBirth(req.getDateOfBirth())
                .role(req.getRole())
                .isVerified(false)
                .twoFactorEmail(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public ProfileResponse toResponse(Profile profile) {
        var b = ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getFirstName() + " " + profile.getLastName())
                .email(profile.getEmail())
                .role(profile.getRole())
                .verified(profile.isVerified())
                .twoFactorEmail(profile.isTwoFactorEmail())
                .dateOfBirth(profile.getDateOfBirth())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .avatar(imageMapper.toImageDto(profile.getAvatar()));

        if (profile.getHotelOwner() != null) {
            b.artist(hotelOwnerMapper.toHotelOwnerPreviewResponse(profile.getHotelOwner()));
        }

        return b.build();
    }


    public SearchProfileResponse toSearchResponse(Profile profile) {
        return SearchProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getFirstName()+" " + profile.getLastName())
                .email(profile.getEmail())
                .role(profile.getRole())
                .dateOfBirth(profile.getDateOfBirth())
                .createdAt(profile.getCreatedAt())
                .avatar(imageMapper.toImageDto(profile.getAvatar()))
                .hotelOwner(profile.getHotelOwner() != null ? artistMapper.toHotelOwnerPreviewResponse(profile.getHotelOwner()) : null)
                .build();
    }


}

