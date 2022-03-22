package com.covid.correlation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ContinentEnum {
    AFRICA("Africa"),
    ASIA("Asia"),
    EUROPE("Europe"),
    SOUTH_AMERICA("South America"),
    NORTH_AMERICA("North America"),
    ANTARCTICA(" Antarctica"),
    OCEANIA("Oceania"),
    ALL("All Continents");


    private String continentName;
    ContinentEnum(String continentName){
        this.continentName=continentName;
    }

    @JsonCreator
    public static ContinentEnum of(String value) {
        try {
            return valueOf(value);
        }catch (Exception e){
            return ALL;
        }
    }

    public String getContinentName() {
        return continentName;
    }

}
