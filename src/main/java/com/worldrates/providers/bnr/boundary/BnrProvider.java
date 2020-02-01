package com.worldrates.providers.bnr.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.providers.bnr.entity.DataSet;
import com.worldrates.providers.bnr.entity.Rate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty("app.providers.bnr.enabled")
public class BnrProvider implements RatesProvider {
    private final String url;
    private final RestTemplate rest;

    public BnrProvider(@Value("${app.providers.bnr.url}") String url, RestTemplate rest) {
        this.url = url;
        this.rest = rest;
    }

    @Override
    public List<ExchangeRate> getDailyRates() {
        DataSet dataSet = rest.getForObject(url, DataSet.class);
        LocalDate pubDate = dataSet.getBody().getCube().get(0).getDate();

        return dataSet.getBody()
                .getCube()
                .get(0)
                .getRates()
                .stream()
                .map(rate -> convert(rate, pubDate))
                .collect(Collectors.toList());
    }

    @Override
    public String getId() {
        return "BNR";
    }

    private ExchangeRate convert(Rate rate, LocalDate pubDate) {
        return new ExchangeRate(
                null,
                getId(),
                "RON",
                rate.getMultiplier(),
                rate.getCurrency(),
                BigDecimal.ONE.divide(rate.getValue(), 6, RoundingMode.HALF_EVEN),
                pubDate,
                null
        );

    }
}
