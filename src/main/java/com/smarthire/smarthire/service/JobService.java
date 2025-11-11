package com.smarthire.smarthire.service;

import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.JobRepository;
import com.smarthire.smarthire.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository,UserRepository userRepository){
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }
    public Job createJob(Job job){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Recruiter not found"));
        job.setRecruiter(recruiter);
        return jobRepository.save(job);
    }
    public List<Job>getAllJobs(){
        return jobRepository.findAll();
    }
    public Job getJobById(Long id){
        return jobRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Job not found"));
    }
    public List<Job>getJobByRecruiter(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("Recruiter not found"));
        return jobRepository.findByRecruiter(recruiter);
    }
    public void delete(Long id){
        jobRepository.deleteById(id);
    }


}
