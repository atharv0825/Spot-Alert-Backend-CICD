package com.spotAlert.backend.Service;

import com.spotAlert.backend.DTO.LoginRequestDTO;
import com.spotAlert.backend.DTO.LoginResponse;
import com.spotAlert.backend.DTO.UserDTO;
import com.spotAlert.backend.Entity.Users;
import com.spotAlert.backend.Repository.UserRepository;
import com.spotAlert.backend.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private VerificationEmailService verificationEmailService;

    public UserDTO registerUser(UserDTO dto){
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email already registered");
        }

        Users user = Users.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(dto.getRole())
                .isVerified(false)
                .verificationToken(verificationEmailService.generateToken())
                .createdAt(LocalDateTime.now())
                .build();
        Users savedUser = userRepository.save(user);


        verificationEmailService.sendVerificationEmail(savedUser);

        return UserDTO.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .role(savedUser.getRole())
                .build();
    }


    public LoginResponse login(LoginRequestDTO loginRequestDTO) {
        Users user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password")
                );


        if (Boolean.FALSE.equals(user.getIsVerified())) {
            throw new IllegalStateException("Please verify your email before logging in");
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtUtil.generateAccessToken(user);

        LoginResponse response = LoginResponse.builder()
                .jwt(token)
                .email(user.getEmail())
                .build();

        return response;
    }
}
