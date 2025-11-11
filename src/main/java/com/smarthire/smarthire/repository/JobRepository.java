package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByRecruiter(User recruiter);
}
