package com.smarthire.smarthire.service;

import com.smarthire.smarthire.dto.JobRequest;
import com.smarthire.smarthire.dto.JobResponse;
import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.Role;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.JobRepository;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
                .skillsRequired(req.getSkillsRequired())
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
                .skillsRequired(job.getSkillsRequired())
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
        Job optionalJob = jobRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Job not found"
                ));
        return mapToResponse(optionalJob);
    }
    public List<JobResponse>getJobByRecruiter(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Recruiter Not Found!!"));

        if(recruiter.getRole() != Role.RECRUITER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only recruiters can view their jobs");
        }
        List <Job> jobs = jobRepository.findByRecruiterId(recruiter.getId());
        return jobs.stream()
                .map(this::mapToResponse)
                .toList();
    }
    public JobResponse updateJob(String id,JobRequest req ,User recruiter) throws AccessDeniedException {
        Job job = jobRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Job not found"));

        if(!job.getRecruiterEmail().equals(recruiter.getEmail())){
            throw new AccessDeniedException("You can only update jobs you created");
        }

        if(req.getTitle()!=null) job.setTitle(req.getTitle());
        if(req.getDescription()!=null)job.setDescription(req.getDescription());
        if(req.getLocation()!=null)job.setLocation(req.getLocation());
        if(req.getJobType()!=null)job.setJobType(req.getJobType());
        if(req.getSalaryRange()!=null)job.setSalaryRange(req.getSalaryRange());
        if(req.getSkillsRequired()!=null)job.setSkillsRequired(req.getSkillsRequired());


        job.setUpdatedAt(LocalDateTime.now());

        Job updated = jobRepository.save(job);

        return mapToResponse(updated);
    }


    public void delete(String jobId,User user){
        Job job = jobRepository.findById(jobId)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Job not found"
                ));
        if(!job.getRecruiterId().equals(user.getId())){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You cannot delete this job"
            );
        }
        jobRepository.delete(job);
    }
}
