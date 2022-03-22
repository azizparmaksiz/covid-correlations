package com.covid.correlation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CovidCorrelationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CovidCorrelationsApplication.class, args);
    }

}
