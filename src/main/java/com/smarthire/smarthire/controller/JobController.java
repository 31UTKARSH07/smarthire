package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.dto.JobRequest;
import com.smarthire.smarthire.dto.JobResponse;
import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.Role;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.repository.UserRepository;
import com.smarthire.smarthire.service.JobService;
import com.smarthire.smarthire.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    //private final UserRepository userRepository;
    private final UserService userService;

    public JobController(JobService jobService, UserRepository userRepository, UserService userService){
        this.jobService = jobService;
        //this.userRepository = userRepository;
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest req){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthorized");
        }
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        requireRecruiterRole(user);
        System.out.println("AUTH ROLE = " + user.getRole());
        System.out.println("EMAIL = " + email);
        JobResponse jobResponse = jobService.createJob(req,user);
        return ResponseEntity.status(HttpStatus.CREATED).body(jobResponse);
    }
    private void requireRecruiterRole(User user) {
        if (user.getRole() != Role.RECRUITER) {
            throw new AccessDeniedException("Only recruiters can perform this action");
        }
    }
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs(){
//        return ResponseEntity.ok().body(jobService.getAllJobs());
        return ResponseEntity.ok(jobService.getAllJobs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse>getJobById(@PathVariable String id){
        JobResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/recruiter")
    public ResponseEntity<List<JobResponse>>getJobByRecruiter(){
        return ResponseEntity.ok(jobService.getJobByRecruiter());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteJob(@PathVariable String id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        if(user.getRole() != Role.RECRUITER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only recruiters can delete jobs");
        }
        jobService.delete(id,user);
        return ResponseEntity.ok("Job deleted successfully");
    }
}
