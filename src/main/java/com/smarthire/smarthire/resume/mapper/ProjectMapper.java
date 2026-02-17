package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Project;
import com.smarthire.smarthire.resume.dto.ProjectDTO;
import com.smarthire.smarthire.resume.utils.DateParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMapper {
    public final DateParser dateParser;

    public ProjectMapper(DateParser dateParser){
        this.dateParser = dateParser;
    }

    public List<Project> convertProjects(List<ProjectDTO> projectsList){
        if(projectsList == null || projectsList.isEmpty()){
            return new ArrayList<>();
        }

        return projectsList.stream()
                .map(proj -> Project.builder()
                        .name(proj.getName())
                        .description(proj.getDescription())
                        .highlights(proj.getHighlights()!=null ? proj.getHighlights() : new ArrayList<>())
                        .keywords(proj.getTechnologies() != null ?
                                List.of(proj.getTechnologies().split(",\\s*")):new ArrayList<>())
                        .url(proj.getLinks())
                        .startDate(dateParser.extractProjectStartDate(proj.getDuration()))
                        .endDate(dateParser.extractProjectEndDate(proj.getDuration()))
                        .roles(proj.getRole() != null ? List.of(proj.getRole()) : new ArrayList<>())
                        .build()
                ).collect(Collectors.toList());
    }
}
