package org.example.booking_appointment.service;


import org.example.booking_appointment.dto.auth.password.SendForgotPasswordRequest;

public interface UserEmailService {

    void sendPasswordResetCode();
    void sendAccountVerificationCode();
    void sendAccountDeletionCode();
    void sendForgotPasswordCode(SendForgotPasswordRequest req);
}
