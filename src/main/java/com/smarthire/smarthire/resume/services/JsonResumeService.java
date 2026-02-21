package com.smarthire.smarthire.resume.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kklisura.cdt.protocol.commands.IO;
import com.smarthire.smarthire.resume.NestedClasses.Education;
import com.smarthire.smarthire.resume.NestedClasses.Skill;
import com.smarthire.smarthire.resume.NestedClasses.Work;
import com.smarthire.smarthire.resume.dto.JSONResumeDTO;
import com.smarthire.smarthire.resume.dto.ResumeData;
import com.smarthire.smarthire.resume.model.ResumeTheme;
import com.smarthire.smarthire.resume.repository.ResumeThemeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class JsonResumeService {
    private final JsonResumeConverter converter;
    private final ResumeThemeRepository themeRepository;
    private final PdfGeneratorService pdfGeneratorService;
    private final ObjectMapper objectMapper;

    private static final String JSONRESUME_API = "https://registry.jsonresume.org";
    private static final OkHttpClient HTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .build();


    public byte[] generateResume(ResumeData resumeData , String themeName){
        log.info("Generating resume with JSONResume theme: {}", themeName);
        try{
            JSONResumeDTO jsonResume = converter.convert(resumeData);
            String html = renderWithTheme(jsonResume,themeName);
            byte[]pdf = pdfGeneratorService.generatePdf(html);
            incrementThemeUsage(themeName);
            log.info("Resume generated successfully");
            return pdf;
        }catch (Exception e){
            log.error("Error generating resume with theme: {}", themeName, e);
            throw new RuntimeException("Failed to generate resume", e);
        }
    }

    private String renderWithTheme(JSONResumeDTO jsonResume , String themeName) throws IOException{
        log.info("Rendering with theme: {}" , themeName);

        String jsonResumeStr = objectMapper.writeValueAsString(jsonResume);

        String html = fetchFromRegistry(jsonResumeStr,themeName);

        if(html != null && !html.isEmpty()){
            return html;
        }

        return renderWithLocalTheme(jsonResume,themeName);
    }

    private String fetchFromRegistry(String jsonResume , String themeName ) {
        try {
            String themeUrl = String.format("https://themes.jsonresume.org/theme/%s" , themeName);

            RequestBody body = RequestBody.create(
                    jsonResume,
                    MediaType.parse("application/json")
            );
            Request request = new Request.Builder()
                    .url(themeUrl)
                    .post(body)
                    .build();

            try(Response response = HTTP_CLIENT.newCall(request).execute()) {
                if(response.isSuccessful() && response.body() != null){
                    return response.body().string();
                }
            }
        }catch (Exception e){
            log.warn("Failed to fetch from JSONResume registry , using fallback" , e);
        }
        return null;
    }


    private String renderWithLocalTheme(JSONResumeDTO jsonResume , String themeName) {
        return generateBasicHtml(jsonResume);
    }

    private String generateBasicHtml(JSONResumeDTO jsonResume) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html><html><head>");
        html.append("<meta charset='UTF-8'>");
        html.append("<title>Resume - ").append(jsonResume.getBasics().getName()).append("</title>");
        html.append("<style>");
        html.append("body { font-family: Arial, sans-serif; max-width: 800px; margin: 40px auto; padding: 20px; }");
        html.append("h1 { color: #2c3e50; }");
        html.append("h2 { color: #34495e; border-bottom: 2px solid #3498db; padding-bottom: 5px; }");
        html.append(".contact { color: #7f8c8d; margin-bottom: 20px; }");
        html.append(".section { margin-bottom: 30px; }");
        html.append(".item { margin-bottom: 15px; }");
        html.append(".item-title { font-weight: bold; }");
        html.append(".item-subtitle { color: #7f8c8d; font-style: italic; }");
        html.append("</style>");
        html.append("</head><body>");

        // Header
        html.append("<h1>").append(jsonResume.getBasics().getName()).append("</h1>");
        html.append("<div class='contact'>");
        html.append(jsonResume.getBasics().getEmail()).append(" | ");
        html.append(jsonResume.getBasics().getPhone());
        html.append("</div>");

        // Summary
        if (jsonResume.getBasics().getSummary() != null) {
            html.append("<p>").append(jsonResume.getBasics().getSummary()).append("</p>");
        }

        // Work Experience
        if (!jsonResume.getWorks().isEmpty()) {
            html.append("<div class='section'><h2>Experience</h2>");
            for (Work work : jsonResume.getWorks()) {
                html.append("<div class='item'>");
                html.append("<div class='item-title'>").append(work.getPosition()).append("</div>");
                html.append("<div class='item-subtitle'>").append(work.getName());
                if (work.getStartDate() != null) {
                    html.append(" | ").append(work.getStartDate());
                    if (work.getEndDate() != null) {
                        html.append(" - ").append(work.getEndDate());
                    }
                }
                html.append("</div>");
                if (work.getSummary() != null) {
                    html.append("<p>").append(work.getSummary()).append("</p>");
                }
                html.append("</div>");
            }
            html.append("</div>");
        }

        // Education
        if (!jsonResume.getEducation().isEmpty()) {
            html.append("<div class='section'><h2>Education</h2>");
            for (Education edu : jsonResume.getEducation()) {
                html.append("<div class='item'>");
                html.append("<div class='item-title'>").append(edu.getStudyType());
                if (edu.getArea() != null) {
                    html.append(" in ").append(edu.getArea());
                }
                html.append("</div>");
                html.append("<div class='item-subtitle'>").append(edu.getInstitution()).append("</div>");
                html.append("</div>");
            }
            html.append("</div>");
        }

        // Skills
        if (!jsonResume.getSkills().isEmpty()) {
            html.append("<div class='section'><h2>Skills</h2>");
            for (Skill skill : jsonResume.getSkills()) {
                html.append("<div class='item'>");
                html.append("<strong>").append(skill.getName()).append(":</strong> ");
                html.append(String.join(", ", skill.getKeywords()));
                html.append("</div>");
            }
            html.append("</div>");
        }

        html.append("</body></html>");

        return html.toString();
    }

    public List<ResumeTheme> getAvailableThemes() {
        return themeRepository.findByActiveTrue();
    }
    private void incrementThemeUsage(String themeName) {
        themeRepository.findByName(themeName).ifPresent(theme -> {
            theme.setDownloadCount(theme.getDownloadCount() + 1);
            themeRepository.save(theme);
        });
    }

    public void initializeDefaultThemes() {
        log.info("Initializing default JSONResume themes");

        String[][] themes = {
                {"elegant", "Elegant", "A clean and professional theme", "modern"},
                {"stackoverflow", "Stack Overflow", "Developer-focused theme inspired by Stack Overflow", "tech"},
                {"kendall", "Kendall", "Simple and elegant resume theme", "minimalist"},
                {"paper", "Paper", "Clean paper-style theme", "classic"},
                {"spartan", "Spartan", "Minimalistic theme", "minimalist"},
                {"short", "Short", "Compact one-page theme", "compact"},
                {"macchiato", "Macchiato", "Warm and welcoming theme", "creative"},
                {"onepage", "One Page", "Everything on one page", "compact"}
        };

        for (String[] themeInfo : themes) {
            if (themeRepository.findByName(themeInfo[0]).isEmpty()) {
                ResumeTheme theme = ResumeTheme.builder()
                        .name(themeInfo[0])
                        .displayName(themeInfo[1])
                        .description(themeInfo[2])
                        .category(themeInfo[3])
                        .npmPackage("jsonresume-theme-" + themeInfo[0])
                        .official(true)
                        .active(true)
                        .previewUrl("https://jsonresume.org/themes/" + themeInfo[0] + ".png")
                        .sourceUrl("https://github.com/jsonresume/jsonresume-theme-" + themeInfo[0])
                        .build();

                themeRepository.save(theme);
                log.info("Initialized theme: {}", themeInfo[1]);
            }
        }
    }


}
