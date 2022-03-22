package com.covid.correlation.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CovidCaseDto implements Serializable {
    private String country;
    private String continent;
    private Long deaths;
    private Long population;
    private String updated;
}
