package com.spotAlert.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdateRequest {
    private UserDetailsByEmailRequest email;
    private UserUpdateList updateList;
}
