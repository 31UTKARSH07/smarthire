package com.smarthire.smarthire.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {
    private String id;
    private String title;
    private String description;
    private String location;
    private String jobType;
    private String salaryRange;
    //private List<String> salaryRequired;
    private List<String> skillsRequired;
    private String recruiterId;
    private String recruiterEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
