package com.smarthire.smarthire.service;

import com.smarthire.smarthire.dto.JobRequest;
import com.smarthire.smarthire.dto.JobResponse;
import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.Role;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.JobRepository;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository,UserRepository userRepository){
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }
    public JobResponse createJob(JobRequest req,User recruiter){

        if(recruiter.getRole() == null || recruiter.getRole() != Role.RECRUITER){
            throw new RuntimeException("Only recruiters can create jobs");
        }

        Job job = Job.builder()
                .title(req.getTitle())
                .description((req.getDescription()))
                .location(req.getLocation())
                .jobType(req.getJobType())
                .salaryRange(req.getSalaryRange())
                .skillRequired(req.getSkillsRequired())
                .recruiterId(recruiter.getId())
                .recruiterEmail(recruiter.getEmail())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Job saved = jobRepository.save(job);
        return mapToResponse(saved);

//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        User recruiter = userRepository.findByEmail(email)
//                .orElseThrow(()->new RuntimeException("Recruiter not found"));
//        job.setRecruiterId(recruiter.getId());
//        job.setRecruiterEmail(recruiter.getEmail());
//        return jobRepository.save(job);
    }
    private JobResponse mapToResponse(Job job){
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .location(job.getLocation())
                .jobType(job.getJobType())
                .salaryRange(job.getSalaryRange())
                .skillsRequired(job.getSkillRequired())
                .recruiterId(job.getRecruiterId())
                .recruiterEmail(job.getRecruiterEmail())
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .build();
    }
    public List<JobResponse>getAllJobs(){
        List<Job>jobs = jobRepository.findAll();

        return jobs.stream()
                .map(job -> JobResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .recruiterEmail(job.getRecruiterEmail())
                        .createdAt(job.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
    public JobResponse getJobById(String id){

    }
    public List<Job>getJobByRecruiter(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Recruiter not found"));
        return jobRepository.findByRecruiterId(recruiter.getId());
    }
    public void delete(String id){
        jobRepository.deleteById(id);
    }


}
