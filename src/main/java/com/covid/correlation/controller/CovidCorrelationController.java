package com.covid.correlation.controller;

import com.covid.correlation.config.ContinentEnumConverter;
import com.covid.correlation.dto.ContinentEnum;
import com.covid.correlation.dto.ResponseCorrelationDto;
import com.covid.correlation.service.CorrelationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/covid/correlation/api")
@AllArgsConstructor
public class CovidCorrelationController {

    private final CorrelationService covidApiService;



    @ApiOperation(value = "", notes = "This rest service provide correlation coefficient of covid death " +
            "and vaccinated count ",
            nickname = "CorrelationCoefficient")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success|OK")})
    @GetMapping(value = "/correlation")
    public ResponseEntity<ResponseCorrelationDto> getCorrelation(@RequestParam(required = false, name="continent") ContinentEnum continent){
        if(continent==null){
            continent=ContinentEnum.ALL;
        }
        return new ResponseEntity(covidApiService.getCorrelationByContinent(continent), HttpStatus.OK);
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(ContinentEnum.class, new ContinentEnumConverter());
    }
}
