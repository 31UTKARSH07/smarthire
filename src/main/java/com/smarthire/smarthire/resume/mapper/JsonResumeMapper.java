package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Meta;
import com.smarthire.smarthire.resume.dto.JSONResumeDTO;
import com.smarthire.smarthire.resume.dto.ResumeData;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class JsonResumeMapper {
    private final BasicsMapper basicsMapper;
    private final WorkMapper workMapper;
    private final EducationMapper educationMapper;
    private final SkillMapper skillMapper;
    private final ProjectMapper projectMapper;

    public JsonResumeMapper(BasicsMapper basicsMapper,WorkMapper workMapper,EducationMapper educationMapper,SkillMapper skillMapper,ProjectMapper projectMapper){
        this.basicsMapper = basicsMapper;
        this.workMapper = workMapper;
        this.educationMapper = educationMapper;
        this.skillMapper = skillMapper;
        this.projectMapper = projectMapper;
    }
    public JSONResumeDTO convert(ResumeData resumeData) {



        log.info("Converting ResumeData to JSONResume format for: {}",resumeData.getName());

        return JSONResumeDTO.builder()
                .basics(basicsMapper.convertBasics(resumeData))
                .works(workMapper.convertWork(resumeData.getExperiences()))
                .education(educationMapper.convertEducation(resumeData.getEducation()))
                .skills(skillMapper.convertSkills(resumeData.getSkills()))
                .projects(projectMapper.convertProjects(resumeData.getProjects()))
                .meta(createMeta())
                .build();
    }
    private Meta createMeta() {
        return Meta.builder()
                .canonical("https://raw.githubusercontent.com/jsonresume/resume-schema/master/schema.json")
                .version("v1.0.0")
                .lastModified(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();
    }
}
