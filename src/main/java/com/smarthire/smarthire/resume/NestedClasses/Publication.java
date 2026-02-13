package com.smarthire.smarthire.resume.NestedClasses;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Publication {
    private String name;
    private String publisher;
    private String releaseDate;
    private String url;
    private String summary;
}
