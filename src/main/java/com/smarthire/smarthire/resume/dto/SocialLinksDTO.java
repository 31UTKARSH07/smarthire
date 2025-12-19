package com.smarthire.smarthire.resume.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinksDTO {
    private String linkedIn;
    private String github;
    private String portfolio;
    private String twitter;
    private String website;

    public boolean hasLinks() {
        return (linkedIn != null && !linkedIn.isEmpty()) ||
                (github != null && !github.isEmpty()) ||
                (portfolio != null && !portfolio.isEmpty()) ||
                (twitter != null && !twitter.isEmpty()) ||
                (website != null && !website.isEmpty());
    }
}
