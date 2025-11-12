package com.smarthire.smarthire.service;

import com.smarthire.smarthire.dto.SignupRequest;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }
    public User register(SignupRequest req){
        if(userRepository.existsByEmail(req.getEmail())){
            throw new RuntimeException("User already registerd with this email");
        }
        User u = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .role(req.getRole())
                .build();
        return userRepository.save(u);
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser(Long id,User updateUser){
        User existingUser = userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User not found with id"+id));
        existingUser.setFirstName(updateUser.getFirstName());
        existingUser.setLastName(updateUser.getLastName());
        existingUser.setEmail(updateUser.getEmail());
        existingUser.setRole(updateUser.getRole());

        if(updateUser.getPassword() != null && !updateUser.getPassword().isEmpty()){
            existingUser.setPassword(encoder.encode(updateUser.getPassword()));
        }
        return userRepository.save(existingUser);
    }
    public void deleteUserById(Long id){
         userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found with email: "+email));
    }
}
