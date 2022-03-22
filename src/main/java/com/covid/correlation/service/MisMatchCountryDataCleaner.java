package com.covid.correlation.service;

import com.covid.correlation.dto.ContinentEnum;
import com.covid.correlation.dto.CovidCaseDto;
import com.covid.correlation.exception.CorrelationNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Slf4j
@NoArgsConstructor
public final class MisMatchCountryDataCleaner{
    private List<CovidCaseDto> casesByContinent;
    private List<CovidVaccinatedDto>vaccinatedByContinent;

    MisMatchCountryDataCleaner(ContinentEnum continent,
                               Map<String, List<CovidCaseDto>> casesMap,
                               Map<String, List<CovidVaccinatedDto>> vaccinesMap){


        if(continent.equals(ContinentEnum.ALL)){
            casesByContinent=casesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
            vaccinatedByContinent=vaccinesMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        }else {
            casesByContinent=casesMap.get(continent.getContinentName());
            vaccinatedByContinent=vaccinesMap.get(continent.getContinentName());
        }

        if(casesByContinent==null || vaccinatedByContinent==null){
            throw new CorrelationNotFoundException("Correlation not found for continent "+continent.getContinentName());
        }
        cleanMisMatchCountries();

    }


    private void cleanMisMatchCountries(){
        Set<String> caseCountries=casesByContinent.stream().map(CovidCaseDto::getCountry).collect(Collectors.toSet());

        Set<String> vaccinatedCountries=vaccinatedByContinent.stream().map(CovidVaccinatedDto::getCountry).collect(Collectors.toSet());

        casesByContinent=casesByContinent.stream().filter(item->vaccinatedCountries.contains(item.getCountry())).collect(Collectors.toList());

        vaccinatedByContinent=vaccinatedByContinent.stream().filter(item->caseCountries.contains(item.getCountry())).collect(Collectors.toList());

        if(casesByContinent.isEmpty() || vaccinatedByContinent.isEmpty()){
            log.error("Correlation countries are not match with for Cases and vaccines");
            throw new CorrelationNotFoundException("Correlation countries are not match with for Cases and vaccines  ");
        }
    }

}
