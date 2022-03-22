package com.covid.correlation.service;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class CovidVaccinatedDto implements Serializable {

    private String country;
    private String continent;
    private Long population;
    @SerializedName("people_vaccinated")
    private Long peopleVaccinated;
    @SerializedName("people_partially_vaccinated")
    private Long peoplePartiallyVaccinated;

}
