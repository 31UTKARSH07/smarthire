package com.smarthire.smarthire.resume.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    private String degree;
    private String institution;
    private String location;
    private String startDate;
    private String endDate;
    private String grade;
    private String description;

    public String getDateRange() {
        if (startDate == null) return "";
        if (endDate == null || endDate.isEmpty()) {
            return startDate + " - Present";
        }
        return startDate + " - " + endDate;
    }
}
