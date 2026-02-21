package com.smarthire.smarthire.resume.services;

import com.smarthire.smarthire.resume.dto.JSONResumeDTO;
import com.smarthire.smarthire.resume.dto.ResumeData;
import com.smarthire.smarthire.resume.mapper.JsonResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonResumeConverter {
    private final JsonResumeMapper jsonResumeMapper;

    public JSONResumeDTO convert(ResumeData resumeData){
        log.info("Starting JSON Resume conversion for user: {}", resumeData.getName());

        return jsonResumeMapper.convert(resumeData);
    }

}
