package com.smarthire.smarthire.resume;

public class TemplateNotFoundException extends RuntimeException {
    public TemplateNotFoundException(String templateName){
        super("Resume template not found: "+ templateName);
    }
    public TemplateNotFoundException(String templateName, Throwable cause) {
        super("Resume template not found: " + templateName, cause);
    }
}
