package org.example.booking_appointment.utils.otp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class OtpService {

    private final SecureRandom secureRandom = new SecureRandom();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String generateOtp() {
        int otp = 100000 + secureRandom.nextInt(900000);
        return String.valueOf(otp);
    }
    public String generateBookingCode() {
        long code = 1000000000L + secureRandom.nextLong(9000000000L); // 10 číslic
        return String.valueOf(code);
    }

    public String encodeOtp(String otp) {
        return passwordEncoder.encode(otp);
    }

    public boolean verifyOtp(String plainOtp, String encodedOtp) {
        return passwordEncoder.matches(plainOtp, encodedOtp);
    }
}
