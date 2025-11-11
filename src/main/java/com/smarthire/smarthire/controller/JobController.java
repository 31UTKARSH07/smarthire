package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService){
        this.jobService = jobService;
    }
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job){
        Job created = jobService.createJob(job);
        return ResponseEntity.ok(created);
    }
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(@RequestBody Job job){
        return ResponseEntity.ok(jobService.getAllJobs());
    }
    @GetMapping("/{id}")
    public ResponseEntity<Job>getJobById(@PathVariable Long id){
        return ResponseEntity.ok(jobService.getJobById(id));
    }
    @GetMapping("/recruiter")
    public ResponseEntity<List<Job>>getJobByRecruiter(){
        return ResponseEntity.ok(jobService.getJobByRecruiter());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteJob(@PathVariable Long id){
        jobService.delete(id);
        return ResponseEntity.ok("Job deleted successfully");
    }
}
