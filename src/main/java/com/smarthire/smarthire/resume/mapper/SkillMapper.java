package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Skill;

import java.util.ArrayList;
import java.util.List;

public class SkillMapper {
    public List<Skill>convertSkills(List<String>skillsList) {
        if(skillsList == null || skillsList.isEmpty()){
            return new ArrayList<>();
        }

        return List.of(
                Skill.builder()
                        .name("Technical Skills")
                        .level("Advanced")
                        .keywords(skillsList)
                        .build()
        );
    }
}
