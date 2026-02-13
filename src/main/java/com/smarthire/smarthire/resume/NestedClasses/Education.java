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
public class Education {
    private String institution;
    private String url;
    private String area;
    private String studyType;
    private String startDate;
    private String endDate;
    private String score;

    @Builder.Default
    private List<String> courses = new ArrayList<>();
}
