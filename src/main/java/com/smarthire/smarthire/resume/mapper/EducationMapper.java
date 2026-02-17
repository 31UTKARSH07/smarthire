package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Education;
import com.smarthire.smarthire.resume.dto.EducationDTO;
import com.smarthire.smarthire.resume.utils.DateParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EducationMapper {
    private final DateParser dateParser;

    public EducationMapper(DateParser dateParser){
        this.dateParser = dateParser;
    }

    public List<Education>convertEducation(List<EducationDTO>educationList){
        if(educationList == null || educationList.isEmpty()){
            return new ArrayList<>();
        }

        return educationList.stream()
                .map(edu -> Education.builder()
                        .institution(edu.getInstitution())
                        .area(extractArea(edu.getDegree()))
                        .studyType(extractStudyType(edu.getDegree()))
                        .startDate(dateParser.formatDate(edu.getStartDate()))
                        .endDate(dateParser.formatDate(edu.getEndDate()))
                        .score(edu.getGrade())
                        .build()
                ).collect(Collectors.toList());
    }

    private String extractArea(String degree){
        if(degree == null) return null;
        if(degree.contains(" in ")) {
            return degree.substring(degree.indexOf(" in ") + 4);
        }
        return degree;
    }

    private String extractStudyType(String degree) {
        if(degree == null) return null;

        if(degree.toLowerCase().contains("bachelor")) return "Bachelor";
        if(degree.toLowerCase().contains("master")) return "Master";
        if(degree.toLowerCase().contains("phd") || degree.toLowerCase().contains("doctorate")) return "PhD";

        return degree.split(" ")[0];
    }

}
