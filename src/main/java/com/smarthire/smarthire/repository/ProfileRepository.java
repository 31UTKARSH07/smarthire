package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile,String> {
    Optional<Profile> findByUserId(String userId);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);
}
