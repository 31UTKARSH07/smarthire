package com.smarthire.smarthire.resume.utils;

import org.springframework.stereotype.Component;

@Component
public class UsernameExtractor {
    public String extractUsername(String url) {
        if(url == null || url.isEmpty()) return null;

        String [] parts = url.split("/");
        return parts.length > 0 ? parts[parts.length - 1] : null;

    }
}
