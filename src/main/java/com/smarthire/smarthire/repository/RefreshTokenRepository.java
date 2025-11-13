package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.RefreshToken;
import com.smarthire.smarthire.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken,String> {
    Optional<RefreshToken>findByToken(String token);
    Optional<RefreshToken>findByUserId(String userId);
    void deleteByUser(User user);
}
