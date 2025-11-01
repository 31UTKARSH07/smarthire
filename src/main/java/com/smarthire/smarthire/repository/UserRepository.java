package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

