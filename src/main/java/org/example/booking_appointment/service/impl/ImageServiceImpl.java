package org.example.booking_appointment.service.impl;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booking_appointment.dto.auth.ProfileResponse;
import org.example.booking_appointment.dto.image.UploadedImageDto;
import org.example.booking_appointment.entity.Avatar;
import org.example.booking_appointment.entity.Profile;
import org.example.booking_appointment.mapper.ProfileMapper;
import org.example.booking_appointment.repository.AvatarRepository;
import org.example.booking_appointment.repository.ProfileRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.ImageService;
import org.example.booking_appointment.utils.cloudinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AuthService authService;
    private final CloudinaryService cloudinaryService;
    private final AvatarRepository avatarRepository;
    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional
    public ProfileResponse changeProfilePicture(MultipartFile image) {
        Profile currentUser = authService.getCurrentProfile();

        String uploadedPublicId = null;

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("No image provided");
        }

        try {
            // 1) pokud má profil avatar, smaž ho (cloud + DB) a odpoj
            if (currentUser.getAvatar() != null) {
                Avatar old = currentUser.getAvatar();
                try {
                    cloudinaryService.deleteImageByPublicId(old.getPublicId());
                } catch (Exception ignore) { /* best effort */ }
                currentUser.setAvatar(null);
                profileRepository.save(currentUser);
                avatarRepository.delete(old);
            }

            // 2) upload nového
            UploadedImageDto uploaded = cloudinaryService.uploadImage(image, "avatars");
            uploadedPublicId = uploaded.getPublicId();

            Avatar newAvatar = cloudinaryService.buildProfileImage(uploaded, currentUser);
            avatarRepository.save(newAvatar);    // persistneme obrázek
            currentUser.setAvatar(newAvatar);             // nastavíme aktuální avatar na profilu
            profileRepository.save(currentUser);          // uložíme profil

            // 3) response přes sjednocený mapper
            return profileMapper.toResponse(currentUser);

        } catch (RuntimeException ex) {
            // DB fail -> zkus smazat v Cloudinary (best effort)
            if (uploadedPublicId != null) {
                try {
                    cloudinaryService.deleteImageByPublicId(uploadedPublicId);
                } catch (Exception cleanupEx) {
                    log.warn("Cloudinary cleanup failed: {}", cleanupEx.getMessage());
                }
            }
            throw ex;
        }
    }

    @Override
    @Transactional
    public ProfileResponse deleteProfilePicture() {
        Profile currentUser = authService.getCurrentProfile();

        Avatar userAvatar = currentUser.getAvatar();
        if (userAvatar == null) {
            return profileMapper.toResponse(currentUser);
        }

        try {
            cloudinaryService.deleteImageByPublicId(userAvatar.getPublicId());
        } catch (Exception ignore) { }

        currentUser.setAvatar(null);
        profileRepository.save(currentUser);

        avatarRepository.delete(userAvatar);

        return profileMapper.toResponse(currentUser);
    }

}