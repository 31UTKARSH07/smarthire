package com.smarthire.smarthire.resume.dto;




import com.fasterxml.jackson.annotation.JsonInclude;
import com.smarthire.smarthire.resume.NestedClasses.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import org.springframework.data.mongodb.core.query.Meta;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JSONResumeDTO {
    private Basics basics;

    private List<Work>works = new ArrayList<>();

    private List<Volunteer> volunteer = new ArrayList<>();

    private List<Education> education = new ArrayList<>();

    private List<Award> awards = new ArrayList<>();

    private List<Certificate> certificates = new ArrayList<>();

    private List<Publication> publications = new ArrayList<>();

    private List<Skill> skills = new ArrayList<>();

    private List<Language> languages = new ArrayList<>();

    private List<Reference> references = new ArrayList<>();

    private List<Project> projects = new ArrayList<>();

    private Meta meta;
}
