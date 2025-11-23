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
import org.springframework.http.ResponseEntity;
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
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));

        if(!job.getRecruiterId().equals(recruiter.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You do not own this job");
        }
        List<Application>apps = applicationRepository.findByJobId(jobId);

        return apps.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    public ApplicationResponse updateStatus(String applicationId, ApplicationStatus status , User recruiter){
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Application not found!"));


        Job job = jobRepository.findById(app.getJobId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found!"));

        if(!job.getRecruiterId().equals(recruiter.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allowed to update this application");
        }
        app.setStatus(status);
        app.setUpdatedAt(LocalDateTime.now());

        Application updated = applicationRepository.save(app);
        return mapToResponse(updated);
    }
    public void withdrawApplication(String applicationId, User student){
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"application not found"));

        if(!app.getStudentId().equals(student.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You cannot withdraw someone else's application");
        }
        applicationRepository.delete(app);
    }
    public List<ApplicationResponse>getMyApplications(String studentId){
        List<Application>apps = applicationRepository.findByStudentId(studentId);

        return apps.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
    private ApplicationResponse mapToResponse(Application app){
        return ApplicationResponse.builder()
                .id(app.getJobId())
                .jobTitle(app.getJobTitle())
                .studentId(app.getStudentId())
                .studentEmail(app.getStudentEmail())
                .resumeUrl(app.getResumeUrl())
                .coverLetter(app.getCoverLetter())
                .status(app.getStatus())
                .appliedAt(app.getAppliedAt())
                .updatedAt(app.getUpdatedAt())
                .build();
    }
}
