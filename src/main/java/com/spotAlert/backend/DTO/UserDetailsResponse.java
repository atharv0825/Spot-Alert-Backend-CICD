package com.spotAlert.backend.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsResponse {
   private String phone;
   private String name;
}
