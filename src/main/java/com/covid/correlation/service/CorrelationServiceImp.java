package com.covid.correlation.service;

import com.covid.correlation.dto.ContinentEnum;
import com.covid.correlation.dto.CovidCaseDto;
import com.covid.correlation.dto.ResponseCorrelationDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class CorrelationServiceImp implements CorrelationService {

    private final FetchCovidService fetchCovidService;

    @Cacheable(value = "correlation", key = "#continent")
    @Override
    public ResponseCorrelationDto getCorrelationByContinent(ContinentEnum continent) {


        Map<String, List<CovidCaseDto>> casesMap = fetchCovidService.getCasesMap();
        Map<String, List<CovidVaccinatedDto>> vaccinesMap = fetchCovidService.getVaccinesMap();

        MisMatchCountryDataCleaner misMatchCountryDataCleaner = new MisMatchCountryDataCleaner(continent, casesMap, vaccinesMap);

        return correlate(continent, misMatchCountryDataCleaner.getCasesByContinent(), misMatchCountryDataCleaner.getVaccinatedByContinent());
    }

    private ResponseCorrelationDto correlate(ContinentEnum continent, List<CovidCaseDto> casesByContinent, List<CovidVaccinatedDto> vaccinatedByContinent) {
        double[][] deathAndVacRates = getDeathAndVaccinatedRates(casesByContinent, vaccinatedByContinent);

        double correlation=correlate(deathAndVacRates);

        // update date is same for whole entries
        String updateDate= casesByContinent.get(0).getUpdated();

        return new ResponseCorrelationDto(correlation, continent.getContinentName(),updateDate);
    }

    private double[][] getDeathAndVaccinatedRates(List<CovidCaseDto> casesByContinent, List<CovidVaccinatedDto> vaccinatedByContinent) {
        List<Double> caseRateList = new ArrayList<>();
        casesByContinent.forEach(ca -> {
                    Long vaccinated = ca.getDeaths();
                    Long population = ca.getPopulation();
                    double val = vaccinated.doubleValue() /population;
                    caseRateList.add(val);
                });

        List<Double> vaccinateRateList = new ArrayList<>();
        vaccinatedByContinent.forEach(ca -> {
                    Long vaccinated = ca.getPeopleVaccinated();
                    Long population = ca.getPopulation();
                    double val = vaccinated.doubleValue() /population;
                    vaccinateRateList.add(val);
                });


        double[] deathRate = caseRateList.stream().mapToDouble(Double::doubleValue).toArray();
        double[] vaccinatedRate = vaccinateRateList.stream().mapToDouble(Double::doubleValue).toArray();

        return new double[][]{deathRate,vaccinatedRate};
    }


    private double correlate(double[][] deathAndVacRates){
        PearsonsCorrelation pearsonsCorrelation=new PearsonsCorrelation();
        return pearsonsCorrelation.correlation(deathAndVacRates[0],deathAndVacRates[1]);
    }




}
