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
public class ProjectDTO {
    private String name;
    private String description;
    private String duration;
    private String role;
    private String technologies;
    private String links;

    @Builder.Default
    private List<String> highlights = new ArrayList<>();
}
