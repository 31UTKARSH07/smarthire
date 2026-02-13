package com.smarthire.smarthire.resume.NestedClasses;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project {
    private String name;
    private String description;

    @Builder.Default
    private List<String>highlights = new ArrayList<>();
    @Builder.Default
    private List<String>keywords = new ArrayList<>();

    private String startDate;
    private String endDate;
    private String url;

    @Builder.Default
    private List<String>roles = new ArrayList<>();

    private String entity;
    private String type;

}
