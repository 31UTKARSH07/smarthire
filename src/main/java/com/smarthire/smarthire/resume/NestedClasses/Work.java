package com.smarthire.smarthire.resume.NestedClasses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Work {
    private String name;
    private String position;
    private String url;
    private String startDate;
    private String endDate;
    private String summary;

    @Builder.Default
    private List<String>highlights = new ArrayList<>();
}
