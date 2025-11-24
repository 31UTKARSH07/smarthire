package com.smarthire.smarthire.model.EmbeddedClasses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private String school;
    private String degree;
    private int startYear;
    private int endYear;
}
