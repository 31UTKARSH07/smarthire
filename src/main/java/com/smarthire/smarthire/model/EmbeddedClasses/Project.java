package com.smarthire.smarthire.model.EmbeddedClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String title;
    private String description;
    private List<String> tech;
    private String projectUrl;
}
