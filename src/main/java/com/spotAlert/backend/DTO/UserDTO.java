package com.spotAlert.backend.DTO;

import com.spotAlert.backend.Entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private String id;
    private String email;
    private String uniqueId;
    private String password;
    private Role role;
}
