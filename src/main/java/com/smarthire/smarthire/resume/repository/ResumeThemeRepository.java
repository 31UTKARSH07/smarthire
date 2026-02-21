package com.smarthire.smarthire.resume.repository;

import com.smarthire.smarthire.resume.model.ResumeTheme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeThemeRepository extends MongoRepository<ResumeTheme,String> {
    List<ResumeTheme>findByActiveTrue();
    Optional<ResumeTheme> findByName(String name);
    List<ResumeTheme> findByOfficialTrue();
    List<ResumeTheme> findByCategory(String category);
    List<ResumeTheme> findTop10ByActiveTrueOrderByDownloadCountDesc();
}
