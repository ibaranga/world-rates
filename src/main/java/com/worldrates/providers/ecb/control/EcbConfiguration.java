package com.worldrates.providers.ecb.control;



import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class EcbConfiguration {

    @Produces
    public EcbCsvParser ecbCsvParser() {
        return EcbCsvParser.defaultParser();
    }
}
