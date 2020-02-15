package com.worldrates.providers.era.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.providers.bnr.entity.DataSet;
import com.worldrates.providers.era.entity.EraRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty("app.providers.era.enabled")
public class EraProvider implements RatesProvider {


    private final String url;
    private final RestTemplate rest;

    public EraProvider(@Value("${app.providers.era.url}") String url, RestTemplate rest) {
        this.url = url;
        this.rest = rest;
    }

    @Override
    public String getId() {
        return "ERA";
    }


    @Override
    public List<ExchangeRate> getDailyRates() {

        EraRates eraRates = rest.getForObject(url, EraRates.class);
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


}
