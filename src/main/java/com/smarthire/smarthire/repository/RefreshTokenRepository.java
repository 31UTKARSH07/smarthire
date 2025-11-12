package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.RefreshToken;
import com.smarthire.smarthire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken>findByToken(String token);

    void deleteByUser(User user);
}
