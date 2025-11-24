package com.smarthire.smarthire.model;

import com.smarthire.smarthire.model.EmbeddedClasses.Education;
import com.smarthire.smarthire.model.EmbeddedClasses.Experience;
import com.smarthire.smarthire.model.EmbeddedClasses.Project;
import com.smarthire.smarthire.model.EmbeddedClasses.SocialLinks;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    private String id;
    private String userId;

    private String headline;
    private String bio;
    private String location;

    private List<Experience> experience;
    private List<Education> education;
    private List<Project> projects;

    private List<String> skills;

    private SocialLinks links;

    private String resumeUrl;

    private LocalDateTime updatedAt;


}
