package org.example.booking_appointment.service;

import org.example.booking_appointment.dto.auth.*;
import org.example.booking_appointment.dto.auth.password.ChangePasswordRequest;
import org.example.booking_appointment.dto.auth.password.ResetPasswordRequest;

public interface ProfileService {
    ProfileResponse register(RegisterRequest req);
    ProfileResponse updateUser(UpdateUserRequest req);

    void deleteUser(DeleteAccountDto otp);
    void changePassword(ChangePasswordRequest req);
    void verifyAccount(VerifyAccountRequest req);
    void forgotPassword(ResetPasswordRequest req);

    SearchProfileResponse findByProfileId(Long id);
}

