package org.example.booking_appointment.service;

import lombok.RequiredArgsConstructor;
import org.example.booking_appointment.utils.otp.OtpService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;


    @Value("${spring.mail.properties.mail.smtp.from}")
    private String sentBy;

    public void sendPostRegisterEmail(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("Welcome");
        message.setText("Thanks for registering your account.");
        mailSender.send(message);
    }

    public void sendResetPasswordCode(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("Password Reset CODE");
        message.setText("This is your password reset code! ---->" + otp + "<----\n\nUse this CODE to proceed with resetting your password");
        mailSender.send(message);
    }

    public void sendAccountDeletionCode(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("Account deletion CODE");
        message.setText("Your account deletion code ---->" + otp + "<----\n\nUse this CODE to proceed with deleting your account");
        mailSender.send(message);
    }

    public void sendAccountVerificationCode(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("Account verification code");
        message.setText("This is your account verification code! ---->" + otp + "<----\n\nUse this CODE to proceed with verification code");
        mailSender.send(message);
    }

    public void send2FAVerificationCode(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("2FA Login code");
        message.setText("This is your 2FA Login code! ---->" + otp + "<----\n\nUse this CODE to proceed with verification code");
        mailSender.send(message);
    }

    public void sendForgotPasswordCode(String toEmail, String otp) {
        var message = new SimpleMailMessage();
        message.setFrom(sentBy);
        message.setTo(toEmail);
        message.setSubject("Forgotten password code");
        message.setText("This is your Forgotten password code! ---->" + otp + "<----\n\nUse this CODE to proceed with resetting your password");
        mailSender.send(message);
    }

}
