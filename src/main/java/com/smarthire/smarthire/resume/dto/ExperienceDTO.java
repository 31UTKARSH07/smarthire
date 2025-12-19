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
public class ExperienceDTO {

    private String jobTitle;
    private String company;
    private String location;
    private String startDate;
    private String endDate;
    private String description;

    @Builder.Default
    private List<String> achievements = new ArrayList<>();

    public String getDateRange() {
        if (startDate == null) return "";
        if (endDate == null || endDate.isEmpty()) {
            return startDate + " - Present";
        }
        return startDate + " - " + endDate;
    }
}
