package org.example.booking_appointment.service;


import lombok.AllArgsConstructor;
import org.example.booking_appointment.entity.Profile;
import org.example.booking_appointment.exception.NotFoundException;
import org.example.booking_appointment.repository.ProfileRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;


    /*public Profile getCurrentProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) return null;

        Long profileId = (Long) authentication.getPrincipal();
        return profileRepository.findById(profileId).orElse(null);
    }*/

    public Profile getCurrentProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("You are not authenticated");
        }

        Long profileId = (Long) authentication.getPrincipal();
        return profileRepository.findById(profileId)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }



}
