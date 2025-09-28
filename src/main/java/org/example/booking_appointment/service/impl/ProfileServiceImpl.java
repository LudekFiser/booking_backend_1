package org.example.booking_appointment.service.impl;
import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.dto.auth.*;
import org.example.booking_appointment.dto.auth.password.ChangePasswordRequest;
import org.example.booking_appointment.dto.auth.password.ResetPasswordRequest;
import org.example.booking_appointment.entity.HotelOwner;
import org.example.booking_appointment.entity.Profile;
import org.example.booking_appointment.entity.User;
import org.example.booking_appointment.enums.ROLE;
import org.example.booking_appointment.exception.*;
import org.example.booking_appointment.exception.password.PasswordException;
import org.example.booking_appointment.exception.password.PasswordResetReqNotMatching;
import org.example.booking_appointment.exception.password.PasswordsDoNotMatchException;
import org.example.booking_appointment.exception.password.PasswordsMatchingException;
import org.example.booking_appointment.mapper.ProfileMapper;
import org.example.booking_appointment.repository.AvatarRepository;
import org.example.booking_appointment.repository.HotelOwnerRepository;
import org.example.booking_appointment.repository.ProfileRepository;
import org.example.booking_appointment.repository.UserRepository;
import org.example.booking_appointment.service.AuthService;
import org.example.booking_appointment.service.ProfileService;
import org.example.booking_appointment.utils.cloudinary.CloudinaryService;
import org.example.booking_appointment.utils.otp.OtpService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final HotelOwnerRepository hotelOwnerRepository;
    private final AvatarRepository avatarRepository;
    private final ProfileMapper profileMapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final CloudinaryService cloudinaryService;
    private final AuthService authService;


    private boolean emailAlreadyUsed(String email) {
        return profileRepository.existsByEmail(email);
    }

    @Override
    public ProfileResponse register(RegisterRequest req) {
        /*if(profileRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email is already used");
        }*/
        if (emailAlreadyUsed(req.getEmail())) {
            throw new IllegalArgumentException("Email is already used");
        }
        if (!Profile.isAdult(req.getDateOfBirth()))  {
            throw new NotOldEnoughException();
        }

        Profile profile = profileMapper.toEntity(req);
        profile.setPassword(passwordEncoder.encode(req.getPassword()));
        profile = profileRepository.save(profile);

        if (req.getRole() == ROLE.HOTEL_OWNER) {
            var hotelOwner = new HotelOwner();
            hotelOwner.setProfile(profile);
            hotelOwner.setNumOfHotels(0);
            hotelOwnerRepository.save(hotelOwner);
            profile.setHotelOwner(hotelOwner);
        } else {
            var user = new User();
            user.setProfile(profile);
            user.setNumOfBookings(0);
            userRepository.save(user);
            profile.setUser(user);
        }

        return profileMapper.toResponse(profile);
    }

    @Override
    public ProfileResponse updateUser(UpdateUserRequest req) {
        Profile currentProfile = authService.getCurrentProfile();

        if (req.getFirstName() != null) {
            currentProfile.setFirstName(req.getFirstName());
        }
        if (req.getLastName() != null) {
            currentProfile.setLastName(req.getLastName());
        }
        if (req.getEmail() != null) {
            if (!currentProfile.getEmail().equals(req.getEmail()) && emailAlreadyUsed(req.getEmail()))
                throw new IllegalArgumentException("Email is already used");
            currentProfile.setEmail(req.getEmail());
        }
        if (req.getTwoFactorEmail() != null) currentProfile.setTwoFactorEmail(req.getTwoFactorEmail());

        currentProfile.setUpdatedAt(LocalDateTime.now());
        profileRepository.save(currentProfile);

        return profileMapper.toResponse(currentProfile);
    }

    @Override
    public void deleteUser(DeleteAccountDto otp) {
        Profile currentProfile = authService.getCurrentProfile();

        // OTP kontrola
        if (currentProfile.getVerificationCode() == null) {
            throw new RuntimeException("Invalid OTP");
        }
        if (!otpService.verifyOtp(otp.getOtp(), currentProfile.getVerificationCode())) {
            throw new RuntimeException("Invalid OTP");
        }
        if (currentProfile.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (currentProfile.getAvatar() != null) {
            var image = currentProfile.getAvatar();
            cloudinaryService.deleteImageByPublicId(image.getPublicId());
            currentProfile.setAvatar(null);
            profileRepository.save(currentProfile);
            avatarRepository.delete(image);
        }

        profileRepository.delete(currentProfile);
    }

    @Override
    public void forgotPassword(ResetPasswordRequest req) {

        if(!req.getNewPassword().equals(req.getRepeatNewPassword())) {
            throw new PasswordException("Passwords do not match");
            //throw new PasswordResetReqNotMatching();
        }

        Profile profile = profileRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (profile.getVerificationCode() == null ||
                profile.getVerificationCodeExpiration() == null ||
                profile.getVerificationCodeExpiration().isBefore(LocalDateTime.now()) ||
                !otpService.verifyOtp(req.getOtp(), profile.getVerificationCode())) {
            throw new RuntimeException("Invalid or expired OTP");
        }

        profile.setPassword(passwordEncoder.encode(req.getNewPassword()));
        profile.setVerificationCode(null);
        profile.setVerificationCodeExpiration(null);
        profileRepository.save(profile);
    }

    @Override
    public SearchProfileResponse findByProfileId(Long id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return profileMapper.toSearchResponse(profile);
    }

    @Override
    public void changePassword(ChangePasswordRequest req) {
        Profile currentProfile = authService.getCurrentProfile();
        if (currentProfile == null) {
            throw new NotFoundException("User not found");
        }

        if (!passwordEncoder.matches(req.getOldPassword(), currentProfile.getPassword())) {
            throw new PasswordException("Old Password does not match!");
            //throw new PasswordsDoNotMatchException();
        }
        if (passwordEncoder.matches(req.getNewPassword(), currentProfile.getPassword())) {
            throw new PasswordException("New password cannot be the same as the Old password!");
            //throw new PasswordsMatchingException();
        }

        // OTP validation
        if (currentProfile.getVerificationCode() == null) {
            throw new RuntimeException("Invalid OTP");
        }
        if (!otpService.verifyOtp(req.getOtp(), currentProfile.getVerificationCode())) {
            throw new RuntimeException("Invalid OTP");
        }
        if (currentProfile.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        currentProfile.setPassword(passwordEncoder.encode(req.getNewPassword()));
        currentProfile.setVerificationCode(null);
        currentProfile.setVerificationCodeExpiration(null);
        profileRepository.save(currentProfile);
    }

    @Override
    public void verifyAccount(VerifyAccountRequest req) {
        Profile currentProfile = authService.getCurrentProfile();

        if (currentProfile.isVerified()) {
            throw new RuntimeException("Account is already verified");
        }
        if (currentProfile.getVerificationCode() == null) {
            throw new RuntimeException("Invalid OTP");
        }
        if (!otpService.verifyOtp(req.getOtp(), currentProfile.getVerificationCode())) {
            throw new RuntimeException("Invalid OTP");
        }
        if (currentProfile.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        currentProfile.setVerificationCode(null);
        currentProfile.setVerificationCodeExpiration(null);
        currentProfile.setVerified(true);
        profileRepository.save(currentProfile);
    }
}

