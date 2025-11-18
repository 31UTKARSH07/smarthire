package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

/*
* this is an Interface which extends MongoRepository Interface
* and provide us all the database methods to us*/

/*Optional<OurUsers> findByEmail(String email);
This ensures that findByEmail will either,
Return the OurUsers object wrapped in an Optional, or
Return an empty Optional if no user is found, allowing orElseThrow to handle the case.*/
