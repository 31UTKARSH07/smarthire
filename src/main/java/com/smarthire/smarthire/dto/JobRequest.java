package com.smarthire.smarthire.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    private String location;
    private String jobType;
    private String salaryRange;
    private List<String> skillsRequired;

}
