package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.dto.AuthResponse;
import com.smarthire.smarthire.dto.LoginRequest;
import com.smarthire.smarthire.dto.SignupRequest;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.security.JwtUtil;
import com.smarthire.smarthire.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,AuthenticationManager authManager,JwtUtil jwtUtil){
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest req){
        User created = userService.register(req);
        created.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest req){
        try{
            System.out.println("Attempting login for email: " + req.getEmail());
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail() , req.getPassword())
            );
            UserDetails ud = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(ud.getUsername());
            System.out.println("Login success for user: "+ ud.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }catch (Exception ex){
            System.out.println("Login failed: "+ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }


}
