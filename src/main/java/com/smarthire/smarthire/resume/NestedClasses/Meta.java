package com.smarthire.smarthire.resume.NestedClasses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Meta {
    private String canonical;
    private String version;
    private String lastModified;
}
