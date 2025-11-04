package com.smarthire.smarthire.security;

import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import com.smarthire.smarthire.model.User;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repo;
    public CustomUserDetailsService(UserRepository repo){
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+email));
        return new CustomUserDetails(user);
    }
}
