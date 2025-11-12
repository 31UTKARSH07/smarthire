package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.dto.AuthResponse;
import com.smarthire.smarthire.dto.LoginRequest;
import com.smarthire.smarthire.dto.SignupRequest;
import com.smarthire.smarthire.model.RefreshToken;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.security.JwtUtil;
import com.smarthire.smarthire.service.RefreshTokenService;
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

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserService userService, AuthenticationManager authManager, JwtUtil jwtUtil, RefreshTokenService refreshTokenService){
        this.userService = userService;
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest req){
        User created = userService.register(req);
        created.setPassword(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    /*this is register controller which call function register form userService where
    * all my logics present and it registers users*/
    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest req){
        try{
            System.out.println("Attempting login for email: " + req.getEmail());
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail() , req.getPassword())
            );
            UserDetails ud = (UserDetails) auth.getPrincipal();
            String accessToken = jwtUtil.generateToken(ud.getUsername());
            System.out.println("Login success for user: "+ ud.getUsername());

            User user = userService.findByEmail(req.getEmail());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

            System.out.println("Login successful for user: "+ud.getUsername());

            return ResponseEntity.ok(Map.of(
                    "accessToken",accessToken,
                    "refreshToken",refreshToken.getToken(),
                    "user",Map.of(
                            "id",user.getId(),
                            "email",user.getEmail(),
                            "role",user.getRole()
                    )
            ));
        }catch (Exception ex){
            System.out.println("Login failed: "+ex.getClass().getSimpleName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    @PostMapping("/refresh")
    public ResponseEntity<?>refreshToken(@RequestBody Map<String , String>request){
        String oldRefreshToken = request.get("refreshToken");

        RefreshToken token = refreshTokenService.findByToken(oldRefreshToken)
                .map(refreshTokenService::verifyExpiry)
                .orElseThrow(()->new RuntimeException("Invalid refresh Token"));

        RefreshToken newToken = refreshTokenService.rotateToken(token);

        String newAccessToken = jwtUtil.generateToken(token.getUser().getEmail());

        return ResponseEntity.ok(Map.of(
                "accessToken",newAccessToken,
                "refreshToken",newToken.getToken()
        ));
    }
}
