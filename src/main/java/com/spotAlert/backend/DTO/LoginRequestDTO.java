package com.spotAlert.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDTO {
    private String email;
    private String uniqueId;
    private String password;
}
