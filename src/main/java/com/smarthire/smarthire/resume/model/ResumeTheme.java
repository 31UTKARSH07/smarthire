package com.smarthire.smarthire.resume.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "resume_themes")
public class ResumeTheme {
    @Id
    private String id;

    private String name;
    private String displayName;
    private String description;
    private String author;
    private String version;
    private String npmPackage;
    private String previewUrl;
    private String sourceUrl;

    @Builder.Default
    private boolean active = true;

    @Builder.Default
    private boolean official = false;

    @Builder.Default
    private int downloadCount = 0;

    private String category;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    private String cachedHtmlTemplate;
    private LocalDateTime cacheExpiry;

}
