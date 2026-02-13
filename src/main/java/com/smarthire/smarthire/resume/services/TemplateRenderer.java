package com.smarthire.smarthire.resume.services;

import com.smarthire.smarthire.resume.dto.ExperienceDTO;
import com.smarthire.smarthire.resume.dto.ResumeData;
import com.smarthire.smarthire.resume.exception.TemplateNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TemplateRenderer {
    private static final String TEMPLATE_BASE_PATH = "resume-templates/";

    public String render(String templateName , ResumeData data){
        log.info("Rendering template: {} for user: {}", templateName, data.getName());

        try{
            String template = loadTemplate(templateName);
            String rendered = replacePlaceHolders(template , data);
            log.info("Template rendered Successfully");
            return rendered;
        } catch (Exception e) {
            log.error("Error rendering template: {}", templateName, e);
            throw new TemplateNotFoundException(templateName, e);
        }
    }

    private String loadTemplate(String templateName) throws Exception{
        String templatePath = TEMPLATE_BASE_PATH + templateName + ".html";
        ClassPathResource resource = new ClassPathResource(templatePath);

        if(!resource.exists()){
            throw new TemplateNotFoundException(templateName);
        }
        return StreamUtils.copyToString(
                resource.getInputStream(),
                StandardCharsets.UTF_8
        );
    }


    private String replacePlaceHolders(String template , ResumeData data){
        String result = template;

        result = replace(result , "NAME",data.getName());
        result = replace(result , "HEADLINE",data.getHeadline());
        result = replace(result , "EMAIL",data.getEmail());
        result = replace(result, "PHONE", data.getPhone());
        result = replace(result , "LOCATION",data.getLocation());
        result = replace(result , "SUMMARY",data.getSummary());

        result = replace(result, "SKILLS", renderSkills(data.getSkills()));
        // Experience
        result = replace(result, "EXPERIENCE", renderExperience(data.getExperiences()));
        // Education
        result = replace(result, "EDUCATION", renderEducation(data.getEducation()));
        // Projects
        result = replace(result, "PROJECTS", renderProjects(data.getProjects()));
        // Social Links
        result = replace(result, "SOCIAL_LINKS", renderSocialLinks(data.getSocialLinks()));

        return result;
    }

    private String renderSkills(List<String> skills) {
        if(skills == null || skills.isEmpty()){
            return "<span class='skill-tag'>No skills listed</span>";
        }
        return skills.stream()
                .map(skill -> "<span class='skill-tag'>"+escapeHTML(skill)+"</span")
                .collect(Collectors.joining("\n"));
    }

    private String replace(String template , String placeholder , String value){
        if(value == null){
            value = "";
        }
        return template.replace("{{"+placeholder+"}}",value);
    }

    private String renderExperience(List<ExperienceDTO>experiences){
        if(experiences == null || experiences.isEmpty()){
            return "<p>No experience listed</p>";
        }
        /* we can also return in this way
        * StringBuilder sb = new StringBuilder();
        * for (Experience exp : experiences) {
    sb.append(renderExperienceItem(exp)).append("\n");
}
return sb.toString();
        * */
        return experiences.stream()
                .map(this::renderExperienceItem)
                .collect(Collectors.joining("\n"));
    }

    private String renderExperienceItem(ExperienceDTO exp){
        StringBuilder html = new StringBuilder();
        html.append("<div class='experience-item'>");
    }




    private String escapeHTML(String text){
        if(text == null){
            return "";
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }


}
