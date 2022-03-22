package com.covid.correlation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseCorrelationDto implements Serializable {
    private double correlation;
    private String continent;
    private String updated;
}
