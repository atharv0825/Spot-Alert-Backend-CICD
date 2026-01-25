package com.spotAlert.backend.Repository;

import com.spotAlert.backend.Entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Users,String> {

    Optional<Users>findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Users> findByVerificationToken(String verificationToken);
}
