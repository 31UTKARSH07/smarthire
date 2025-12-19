package com.smarthire.smarthire.model.EmbeddedClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    private String title;
    private String company;
    private String role;
    private String startDate;
    private String endDate;
    private String description;
    private List<String> bullets;
}
