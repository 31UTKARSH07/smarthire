package com.smarthire.smarthire.resume.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeRequest {
    private String templateName;
    private Boolean autofill;
    private String userId;
    private ResumeData customData;

    public boolean isValid(){
        if(templateName == null || !templateName.isEmpty()) return false;
        if(Boolean.TRUE.equals(autofill)){
            return userId != null && !userId.isEmpty();
        }else {
            return customData != null && customData.isValid();
        }
    }
}
