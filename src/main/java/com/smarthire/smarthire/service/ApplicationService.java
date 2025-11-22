package com.smarthire.smarthire.service;

import com.smarthire.smarthire.dto.ApplicationRequest;
import com.smarthire.smarthire.dto.ApplicationResponse;
import com.smarthire.smarthire.model.Application;
import com.smarthire.smarthire.model.ApplicationStatus;
import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.ApplicationRepository;
import com.smarthire.smarthire.repository.JobRepository;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository,UserRepository userRepository,JobRepository jobRepository){
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }
    public ApplicationResponse applyToJob(ApplicationRequest req, User student){
        Job job = jobRepository.findById(req.getJobId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));
        if(applicationRepository.existsByStudentIdAndJobId(student.getId(),job.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You already applied for this job");
        }
        Application app = Application.builder()
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .studentId(student.getId())
                .studentEmail(student.getEmail())
                .resumeUrl(req.getResumeUrl())
                .coverLetter(req.getCoverLetter())
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Application saved = applicationRepository.save(app);
        return mapToResponse(saved);

    }
    public List<ApplicationResponse> getApplicationsForJob(String jobId,User recruiter){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"job not found"));

        if(!job.getRecruiterId().equals(recruiter.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You cannot view applicants for this job");
        }

        List<Application>applications = applicationRepository.findByJobId(jobId);

        return applications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public ApplicationResponse updateStatus(String applicationId, ApplicationStatus status , User recruiter){

    }
    public void withdrawApplication(String applicationId,User student){

    }
    private ApplicationResponse mapToResponse(Application app){
        return ApplicationResponse.builder()

    }
}
