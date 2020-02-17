package com.worldrates.providers.era.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.providers.era.entity.EraRates;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class EraProvider implements RatesProvider {


    private final String url;
    private final Client client;

    @Inject
    public EraProvider(@ConfigProperty(name = "app.providers.era.url") String url, Client client) {
        this.url = url;
        this.client = client;
    }

    @Override
    public String getId() {
        return "ERA";
    }


    @Override
    public List<ExchangeRate> getDailyRates() {

        EraRates eraRates = client.target(url).request().get(EraRates.class);
        return eraRates.getRates().entrySet()
                .stream()
                .map(entry -> getEntryExchangeRateFunction(eraRates, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private ExchangeRate getEntryExchangeRateFunction(EraRates eraRates, String currency, BigDecimal rate) {
        return new ExchangeRate(
                null,
                getId(),
                eraRates.getBase(),
                BigDecimal.ONE,
                currency,
                rate,
                LocalDate.parse(eraRates.getDate(), DateTimeFormatter.ISO_DATE),
                LocalDateTime.now()
        );
    }

    //todo: add unit test

}
