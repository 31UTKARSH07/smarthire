package com.smarthire.smarthire.dto;

import com.smarthire.smarthire.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    private String id;
    private String jobId;
    private String jobTitle;

    private String studentId;
    private String studentEmail;

    private String resumeUrl;
    private String coverLetter;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
}
