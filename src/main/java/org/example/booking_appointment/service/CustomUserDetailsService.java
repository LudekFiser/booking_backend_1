package org.example.booking_appointment.service;


import lombok.AllArgsConstructor;
import org.example.booking_appointment.entity.Profile;
import org.example.booking_appointment.repository.ProfileRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: " + email));

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + profile.getRole().name()));

        return new User(
                profile.getEmail(),
                profile.getPassword(),
                authorities
        );
    }






}
