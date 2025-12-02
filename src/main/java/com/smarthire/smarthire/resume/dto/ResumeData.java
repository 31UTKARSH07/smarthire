package com.smarthire.smarthire.resume.dto;

import com.smarthire.smarthire.model.EmbeddedClasses.Education;
import com.smarthire.smarthire.model.EmbeddedClasses.Experience;
import com.smarthire.smarthire.model.EmbeddedClasses.Project;

import java.util.List;

public class ResumeData {
    private String name;
    private String email;
    private String phone;
    private String location;
    private String tagline;
    private String summary;

    private List<Experience> experiences;
    private List<Education> education;
    private List<Project> projects;

    private List<String> skills;
    private String githubUsername;
    private String portfolioUrl;
}
