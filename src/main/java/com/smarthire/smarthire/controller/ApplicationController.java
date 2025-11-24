package com.smarthire.smarthire.controller;

import com.smarthire.smarthire.dto.ApplicationRequest;
import com.smarthire.smarthire.dto.ApplicationResponse;
import com.smarthire.smarthire.model.ApplicationStatus;
import com.smarthire.smarthire.model.Role;
import com.smarthire.smarthire.model.User;
import com.smarthire.smarthire.service.ApplicationService;
import com.smarthire.smarthire.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static com.smarthire.smarthire.model.Role.STUDENT;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;
    private final UserService userService;

    public ApplicationController(ApplicationService applicationService, UserService userService) {
        this.applicationService = applicationService;
        this.userService = userService;
    }
    @PostMapping("/apply")
    public ResponseEntity<ApplicationResponse>apply(@RequestBody ApplicationRequest req){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedIn = userService.findByEmail(email);
        if(loggedIn.getRole() != STUDENT){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only Students can apply to jobs");
        }
        ApplicationResponse response = applicationService.applyToJob(req,loggedIn);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/my")
    public ResponseEntity<List<ApplicationResponse>>myApplications(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedIn = userService.findByEmail(email);

        if(loggedIn.getRole() != STUDENT){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only students can view their applications");
        }
        List<ApplicationResponse> apps = applicationService.getMyApplications(loggedIn.getId());
        return ResponseEntity.ok(apps);
    }
    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ApplicationResponse>>getApplicationsForJob(@PathVariable String jobId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userService.findByEmail(email);

        if(recruiter.getRole() != Role.RECRUITER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only recruiters can view job applications");
        }

        List<ApplicationResponse>apps = applicationService.getApplicationsForJob(jobId,recruiter);
        return ResponseEntity.ok(apps);
    }
    @PutMapping("/{applicationId}/status")
    public ResponseEntity<ApplicationResponse>updateStatus(@PathVariable String applicationId, @RequestBody Map<String ,String> requestBody){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User recruiter = userService.findByEmail(email);

        if(recruiter.getRole() != Role.RECRUITER){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You are not allow to update applications!!");
        }

        String statusString = requestBody.get("status");
        ApplicationStatus status = ApplicationStatus.valueOf(statusString.toUpperCase());

        ApplicationResponse updated = applicationService.updateStatus(applicationId,status,recruiter);

        return ResponseEntity.ok(updated);

    }
    @DeleteMapping("/{applicationId}")
    public ResponseEntity<String>withdraw(@PathVariable String applicationId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User student = userService.findByEmail(email);
        if(student.getRole() != STUDENT){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only students can withdraw applications");

        }
        applicationService.withdrawApplication(applicationId,student);
        return ResponseEntity.ok("Application withdrawn successfully");
    }
}
