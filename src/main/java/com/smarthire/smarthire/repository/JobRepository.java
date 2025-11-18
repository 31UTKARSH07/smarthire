package com.smarthire.smarthire.repository;

import com.smarthire.smarthire.model.Job;
import com.smarthire.smarthire.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface JobRepository extends MongoRepository<Job,String> {
    List<Job> findByRecruiterId(String recruiterId);
    List<Job> findByTitleContainingIgnoreCase(String title);
}
