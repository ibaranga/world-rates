package com.worldrates.providers.bnr.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.providers.bnr.entity.DataSet;
import com.worldrates.providers.bnr.entity.Rate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class BnrProvider implements RatesProvider {
    private final String url;
    private final Client client;

    @Inject
    public BnrProvider(@ConfigProperty(name = "app.providers.bnr.url") String url, Client client) {
        this.url = url;
        this.client = client;
    }

    @Override
    public List<ExchangeRate> getDailyRates() {
        DataSet dataSet = client.target(url).request().get(DataSet.class);
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
