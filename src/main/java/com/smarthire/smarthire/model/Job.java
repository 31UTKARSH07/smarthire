package com.smarthire.smarthire.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "jobs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job {
    @Id
    private String id;

    private String title;
    private String description;
    private String location;
    private String jobType;
    private String salaryRange;
    private List<String>skillsRequired;

    private String recruiterId;
    private String recruiterEmail;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
