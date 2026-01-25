package com.spotAlert.backend.Controller;

import com.spotAlert.backend.DTO.LoginRequestDTO;
import com.spotAlert.backend.DTO.LoginResponse;
import com.spotAlert.backend.DTO.UserDTO;
import com.spotAlert.backend.Service.AuthService;
import com.spotAlert.backend.Service.EmailService;
import com.spotAlert.backend.Service.VerificationEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationEmailService verificationEmailService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = authService.registerUser(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {

        verificationEmailService.verifyUser(token);

        return ResponseEntity.ok("Email verified successfully. You may now log in.");
    }

}
