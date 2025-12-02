package com.smarthire.smarthire.resume.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "resume_templates")
public class TemplateMeta {
    @Id
    private String id;

    private String name;
    private String displayName;
    private String description;
    private String templateDir;
}
