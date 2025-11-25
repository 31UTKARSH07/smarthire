package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.dto.ProfileResponse;
import com.smarthire.smarthire.dto.ProfileUpdateRequest;
import com.smarthire.smarthire.model.Profile;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.service.ProfileService;
import com.smarthire.smarthire.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final UserService userService;


    public ProfileController(ProfileService profileService,UserService userService){
        this.profileService = profileService;
        this.userService = userService;
    }
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile() {
        return ResponseEntity.ok(profileService.getMyProfile());
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileResponse>updateProfile(@RequestBody ProfileUpdateRequest req){
        ProfileResponse updated = profileService.updateProfile(req);
        return ResponseEntity.ok(updated);
    }
}
