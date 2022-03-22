package com.covid.correlation.service;

import com.covid.correlation.dto.CovidCaseDto;
import com.covid.correlation.exception.ServerException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FetchCovidService {
    @Value("${covid.api.url}")
    private String covidApiUrl;
    @Value("${covid.api.cases.endpoint}")
    private String casesEndPoint;
    @Value("${covid.api.vaccines.endpoint}")
    private String vaccinesEndPoint;


    @Cacheable(value = "all-cases")
    public Map<String,List<CovidCaseDto>> getCasesMap(){

        Map<String,List<CovidCaseDto>> map= Objects.requireNonNull(fetchAllCases()).values()
                .stream()
                .flatMap(item->item.values().stream())
                .filter(x-> x.getContinent() != null)
                .collect(Collectors.groupingBy(CovidCaseDto::getContinent));

        return map;
    }
    @Cacheable(value = "all-vaccines")
    public Map<String,List<CovidVaccinatedDto>> getVaccinesMap(){

        Map<String,List<CovidVaccinatedDto>> map= Objects.requireNonNull(fetchAllVaccines()).values()
                .stream()
                .flatMap(item->item.values().stream())
                .filter(x-> x.getContinent() != null)
                .collect(Collectors.groupingBy(CovidVaccinatedDto::getContinent));

        return map;
    }



    private Map<String, Map<String, CovidVaccinatedDto>> fetchAllVaccines(){
//            String file = "src/main/resources/all-vaccines-data.json";
//           String json = readFileAsString(file);
            String json = getCovidValues(vaccinesEndPoint);
            Type vaccinesType = new TypeToken<Map<String, Map<String, CovidVaccinatedDto>>>() {}.getType();
            Gson gson =  new Gson();
            return gson.fromJson(json, vaccinesType);

    }

    private Map<String, Map<String, CovidCaseDto>> fetchAllCases(){
//            String file = "src/main/resources/all-cases-data.json";
//           String json = readFileAsString(file);
           String json = getCovidValues(casesEndPoint);

            Type vaccinesType = new TypeToken<Map<String, Map<String, CovidCaseDto>>>() {}.getType();
            Gson gson =  new Gson();
            return gson.fromJson(json, vaccinesType);

    }

    //TODO: Instead of redis, you can save response data to the Json file,
    // and with a scheduling service you can update local json file. So you can decrease remote api call
//    private static String readFileAsString(String file) throws Exception {
//        return new String(Files.readAllBytes(Paths.get(file)));
//    }

    private String getCovidValues(String endPoint) {

        String url = covidApiUrl +endPoint;

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            log.error("Unable to fetch data for endpoint {}, errorMessage= {}",endPoint,e.getMessage());
            throw new ServerException("Covid server api unreachable");
        }
    }

}
