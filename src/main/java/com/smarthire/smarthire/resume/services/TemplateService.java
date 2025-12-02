package com.smarthire.smarthire.resume.services;

import com.smarthire.smarthire.resume.model.TemplateMeta;
import com.smarthire.smarthire.resume.repo.TemplateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

// This class will act as a manager for my resume templates.it handle files(uploading and storing)
// and keep track of them in DB.

@Service
public class TemplateService {
    private final TemplateRepository repo;
    private final Path templateBase;

    public TemplateService(TemplateRepository repo , @Value("${resume.templates.base-dir:/tmp/resume-templates}") String baseDir) throws IOException{
        this.repo = repo;
        this.templateBase = Paths.get(baseDir);
        Files.createDirectories(this.templateBase);
    }

    public TemplateMeta uploadTemplate(MultipartFile zipOrTextFile , String displayName,String description) throws IOException{
        String tplName = "tpl-"+ UUID.randomUUID().toString().substring(0,8);
        Path targetDir = templateBase.resolve(tplName);
        Files.createDirectories(targetDir);

        if(zipOrTextFile.getOriginalFilename().endsWith(".zip")){
            Path zipPath = targetDir.resolve(zipOrTextFile.getOriginalFilename());

            Files.copy(zipOrTextFile.getInputStream(),zipPath, StandardCopyOption.REPLACE_EXISTING);

            try (java.util.zip.ZipInputStream zis = new java.util.zip.ZipInputStream(new BufferedInputStream(Files.newInputStream(zipPath)))){
                java.util.zip.ZipEntry entry;
                while((entry = zis.getNextEntry()) != null){
                    Path out = targetDir.resolve(entry.getName());
                    if(entry.isDirectory()){
                        Files.createDirectories((out));
                    }else{
                        Files.createDirectories(out.getParent());
                        Files.copy(zis,out,StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            Files.deleteIfExists(zipPath);
        }else {
            Path out = targetDir.resolve(zipOrTextFile.getOriginalFilename());
            Files.copy(zipOrTextFile.getInputStream(),out,StandardCopyOption.REPLACE_EXISTING);
        }

        TemplateMeta meta = TemplateMeta.builder()
                .name(tplName)
                .displayName(displayName)
                .description(description)
                .templateDir(targetDir.toAbsolutePath().toString())
                .build();
        return repo.save(meta);
    }
}
