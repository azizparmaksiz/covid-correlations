package com.covid.correlation.config;

import com.covid.correlation.dto.ContinentEnum;

import java.beans.PropertyEditorSupport;

public class ContinentEnumConverter extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        String capitalized = text.toUpperCase();
        ContinentEnum currency = ContinentEnum.of(capitalized);
        setValue(currency);
    }
}


