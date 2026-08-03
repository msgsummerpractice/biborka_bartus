package com.example.project.security;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private static final SecureRandom RANDOM = new SecureRandom();

    private final ConcurrentHashMap<String, OtpEntry> otpStore = new ConcurrentHashMap<>();

    private record OtpEntry(String code, long expiresAtMillis) {}

    public String generateOtp(String username) {
        String code = String.format("%06d", RANDOM.nextInt(1_000_000)); // 6-digit code
        long expiresAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5);
        otpStore.put(username, new OtpEntry(code, expiresAt));

        System.out.println("=== OTP for " + username + ": " + code + " ===");

        return code;
    }

    public boolean validateOtp(String username, String submittedCode) {
        OtpEntry entry = otpStore.get(username);
        if (entry == null) return false;

        boolean valid = entry.code().equals(submittedCode)
                && System.currentTimeMillis() < entry.expiresAtMillis();

        if (valid) {
            otpStore.remove(username);
        }
        return valid;
    }
}