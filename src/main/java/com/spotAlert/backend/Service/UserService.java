package com.spotAlert.backend.Service;

import com.spotAlert.backend.DTO.UserDetailsByEmailRequest;
import com.spotAlert.backend.DTO.UserDetailsResponse;
import com.spotAlert.backend.DTO.UserUpdateList;
import com.spotAlert.backend.DTO.UserUpdateRequest;
import com.spotAlert.backend.Entity.Users;
import com.spotAlert.backend.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetailsResponse getUserDetails(UserDetailsByEmailRequest request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email: " + request.getEmail())
                );
        return new UserDetailsResponse(
                user.getPhone(),
                user.getName()
        );
    }

    public UserUpdateList setUserUpdateList(UserUpdateRequest request) {
        Users user = userRepository.findByEmail(request.getEmail().getEmail())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email: " + request.getEmail())
                );

        if(request.getUpdateList().getName() != null){
            user.setName(request.getUpdateList().getName());
        }
        if(request.getUpdateList().getPhone() != null){
            user.setPhone(request.getUpdateList().getPhone());
        }

        user =  userRepository.save(user);

        return new UserUpdateList(
                user.getName(),
                user.getPhone()
        );
    }
}
