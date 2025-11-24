package com.smarthire.smarthire.service;

import com.smarthire.smarthire.dto.ApplicationRequest;
import com.smarthire.smarthire.dto.ProfileResponse;
import com.smarthire.smarthire.dto.ProfileUpdateRequest;
import com.smarthire.smarthire.model.Profile;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

public class ProfileService {
    private final UserService userService;
    private final ProfileRepository profileRepository;
    public ProfileService(UserService userService , ProfileRepository profileRepository){
        this.userService = userService;
        this.profileRepository = profileRepository;
    }
    public ProfileResponse getMyProfile(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);

        Optional<Profile>optionalProfile = profileRepository.findByUserId(user.getId());

//        if(profile.isEmpty()){
//            Profile newProfile = new Profile();
//            newProfile.setUserId(user.getId());
//            newProfile.setUpdatedAt(LocalDateTime.now());
//            profileRepository.save(newProfile);
//
//        }
        Profile profile;
        if(optionalProfile.isEmpty()){
            profile = Profile.builder()
                    .userId(user.getId())
                    .updatedAt(LocalDateTime.now())
                    .build();

            profileRepository.save(profile);
        }else {
            profile = optionalProfile.get();
        }
        return mapToResponse(profile);
    }

    private ProfileResponse updateProfile(@PathVariable ProfileUpdateRequest req){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);

        Profile profile = profileRepository.findByUserId(user.getId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"profile not found"));

        if(req.getHeadline()!=null)
            profile.setHeadline(req.getHeadline());

        if (req.getBio() != null)
            profile.setBio(req.getBio());

        if (req.getLocation() != null)
            profile.setLocation(req.getLocation());

        if (req.getSkills() != null)
            profile.setSkills(req.getSkills());

        if (req.getEducation() != null)
            profile.setEducation(req.getEducation());

        if (req.getExperience() != null)
            profile.setExperience(req.getExperience());

        if (req.getProjects() != null)
            profile.setProjects(req.getProjects());

        if (req.getLinks() != null)
            profile.setLinks(req.getLinks());

        if (req.getResumeUrl() != null)
            profile.setResumeUrl(req.getResumeUrl());

        profile.setUpdatedAt(LocalDateTime.now());

        Profile updated = profileRepository.save(profile);

        return mapToResponse(updated);
    }

    private ProfileResponse mapToResponse(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())

                .headline(profile.getHeadline())
                .bio(profile.getBio())
                .location(profile.getLocation())

                .experience(profile.getExperience())
                .education(profile.getEducation())
                .projects(profile.getProjects())

                .skills(profile.getSkills())
                .links(profile.getLinks())

                .resumeUrl(profile.getResumeUrl())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

}
