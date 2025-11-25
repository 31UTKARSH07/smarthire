package com.smarthire.smarthire.model.EmbeddedClasses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    private String school;
    private String degree;
    private Integer startYear;
    private Integer endYear;
}
