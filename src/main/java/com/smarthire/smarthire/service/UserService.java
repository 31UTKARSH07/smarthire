package com.smarthire.smarthire.service;

import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user){
        return userRepository.save(user);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser(Long id,User updateUser){
        return userRepository.save(updateUser);
    }
    public User deleteUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

}
