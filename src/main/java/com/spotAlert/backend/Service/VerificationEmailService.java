package com.spotAlert.backend.Service;

import com.spotAlert.backend.Entity.Users;
import com.spotAlert.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationEmailService {

    private final EmailService emailService;
    private final UserRepository userRepository;

    @Value("${spotalert.verification.url}")
    private String baseUrl;

    public VerificationEmailService(EmailService emailService, UserRepository userRepository) {
        this.emailService = emailService;
        this.userRepository = userRepository;
    }

    public void sendVerificationEmail(Users user) {

        String verificationLink =
                baseUrl + "/api/auth/verify?token=" + user.getVerificationToken();

        String subject = "Verify your SpotAlert account";

        String body = """
            <div style="font-family: Arial, sans-serif">
                <h2>Welcome to SpotAlert 🚦</h2>
                <p>Please verify your email to activate your account.</p>
                <a href="%s">Verify Email</a>
                <p>If you didn’t sign up, ignore this email.</p>
            </div>
        """.formatted(verificationLink);

        emailService.sendHtmlMail(user.getEmail(), subject, body);
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public boolean verifyUser(String token) {

        Users user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification token"));

        user.setIsVerified(true);
        user.setVerificationToken(null);

        userRepository.save(user);
        return true;
    }
}
