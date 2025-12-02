package com.smarthire.smarthire.resume.repo;

import com.smarthire.smarthire.resume.model.TemplateMeta;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemplateRepository extends MongoRepository<TemplateMeta,String> {
    Optional<TemplateMeta> findByName(String name);
}
