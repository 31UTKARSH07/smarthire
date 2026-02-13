package com.smarthire.smarthire.resume.NestedClasses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Basics {
    private String name;
    private String label;
    private String image;
    private String email;
    private String phone;
    private String url;
    private String summary;
    private Location location;

    private List<Profile> profiles = new ArrayList<>();
}
