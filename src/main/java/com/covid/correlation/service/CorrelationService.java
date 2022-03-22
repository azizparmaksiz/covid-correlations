package com.covid.correlation.service;

import com.covid.correlation.dto.ContinentEnum;
import com.covid.correlation.dto.ResponseCorrelationDto;

public interface CorrelationService {
    ResponseCorrelationDto getCorrelationByContinent(ContinentEnum continent);
}
