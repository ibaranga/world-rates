package com.worldrates.providers.ecb.control;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EcbConfiguration {

    @Bean
    public EcbCsvParser ecbCsvParser() {
        return EcbCsvParser.defaultParser();
    }
}
