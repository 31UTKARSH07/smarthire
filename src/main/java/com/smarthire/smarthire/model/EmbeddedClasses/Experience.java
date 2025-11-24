package com.smarthire.smarthire.model.EmbeddedClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Experience {
    private String company;
    private String role;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
}
