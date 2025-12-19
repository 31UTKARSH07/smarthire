package com.smarthire.smarthire.resume.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeData {
    private String name;
    private String headline;
    private String email;
    private String phone;
    private String location;
    private String summary;

    @Builder.Default
    private List<ExperienceDTO> experiences = new ArrayList<>();

    @Builder.Default
    private List<EducationDTO> education = new ArrayList<>();

    @Builder.Default
    private List<ProjectDTO> projects = new ArrayList<>();

    @Builder.Default
    private List<String> skills = new ArrayList<>();

    private SocialLinksDTO socialLinks;

    public boolean isValid(){
        return name != null && !name.isEmpty() &&
                email != null && !email.isEmpty();
    }
}
