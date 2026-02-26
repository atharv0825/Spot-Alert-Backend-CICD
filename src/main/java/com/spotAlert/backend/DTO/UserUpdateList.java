package com.spotAlert.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserUpdateList {
    private String name;
    private String phone;
}
