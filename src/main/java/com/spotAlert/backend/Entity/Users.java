package com.spotAlert.backend.Entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String uniqueId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    private Boolean isVerified = false;
    private String verificationToken;

    private LocalDateTime createdAt;
}
