package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Work;
import com.smarthire.smarthire.resume.dto.ExperienceDTO;
import com.smarthire.smarthire.resume.utils.DateParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WorkMapper {
    public final DateParser dateParser;

    public WorkMapper(DateParser dateParser){
        this.dateParser = dateParser;
    }
    public List<Work> convertWork(List<ExperienceDTO> experiences){
        if(experiences == null || experiences.isEmpty()) {
            return new ArrayList<>();
        }

        return experiences.stream()
                .map(exp -> Work.builder()
                        .name(exp.getCompany())
                        .position(exp.getJobTitle())
                        .startDate(dateParser.formatDate(exp.getStartDate()))
                        .endDate(dateParser.formatDate(exp.getEndDate())) // I think here I show use extract date method let see ......
                        .summary(exp.getDescription())
                        .highlights(exp.getAchievements() != null ? exp.getAchievements() : new ArrayList<>())
                        .build()
                ).collect(Collectors.toList());
    }
}
