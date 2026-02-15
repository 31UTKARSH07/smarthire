package com.smarthire.smarthire.resume.mapper;

import com.smarthire.smarthire.resume.NestedClasses.Basics;
import com.smarthire.smarthire.resume.NestedClasses.Profile;
import com.smarthire.smarthire.resume.dto.ResumeData;
import com.smarthire.smarthire.resume.dto.SocialLinksDTO;
import com.smarthire.smarthire.resume.utils.LocationParser;
import com.smarthire.smarthire.resume.utils.UsernameExtractor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class BasicsMapper {
    private final LocationParser locationParser;
    private final UsernameExtractor usernameExtractor;

    public BasicsMapper(LocationParser locationParser, UsernameExtractor usernameExtractor){
        this.locationParser = locationParser;
        this.usernameExtractor = usernameExtractor;
    }
    public Basics convertBasics(ResumeData data){

        Basics.BasicsBuilder builder = Basics.builder()
                .name(data.getName())
                .label(data.getHeadline())
                .email(data.getEmail())
                .phone(data.getPhone())
                .summary(data.getSummary());

        if(data.getLocation() != null) {
            builder.location(locationParser.parseLocation(data.getLocation()));
        }

        if(data.getSocialLinks() != null) {
            builder.profiles(convertSocialLinks(data.getSocialLinks()));
        }

        return builder.build();
    }

    private List<Profile> convertSocialLinks(SocialLinksDTO links){
        List<Profile> profiles = new ArrayList<>();

        if(links.getLinkedIn() != null && !links.getLinkedIn().isEmpty()){
            profiles.add(Profile.builder()
                    .network("GitHub")
                    .url(links.getGithub())
                    .username(usernameExtractor.extractUsername(links.getGithub()))
                    .build()
            );
        }
        if(links.getTwitter()!=null && !links.getTwitter().isEmpty()){
            profiles.add(Profile.builder()
                    .network("Twitter")
                    .url(links.getTwitter())
                    .username(usernameExtractor.extractUsername(links.getTwitter()))
                    .build()
            );


        }
        if(links.getWebsite() != null && !links.getWebsite().isEmpty()){
            profiles.add(Profile.builder()
                    .network("Website")
                    .url(links.getWebsite())
                    .build()
            );
        }
        return profiles;
    }

}
