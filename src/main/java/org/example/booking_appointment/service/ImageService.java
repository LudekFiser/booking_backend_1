package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.auth.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ProfileResponse changeProfilePicture(MultipartFile image);
    ProfileResponse deleteProfilePicture();
}
