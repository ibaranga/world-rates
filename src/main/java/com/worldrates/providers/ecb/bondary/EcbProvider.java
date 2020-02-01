package com.worldrates.providers.ecb.bondary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.providers.ecb.control.EcbCsvParser;
import com.worldrates.providers.ecb.entity.EcbRates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

@Component
@ConditionalOnProperty("app.providers.ecb.enabled")
public class EcbProvider implements RatesProvider {

    private final String url;
    private final EcbCsvParser ecbCsvParser;

    public EcbProvider(@Value("${app.providers.ecb.url}") String url, EcbCsvParser ecbCsvParser) {
        this.url = url;
        this.ecbCsvParser = ecbCsvParser;
    }

    @Override
    public String getId() {
        return "ECB";
    }

    @Override
    public List<ExchangeRate> getDailyRates() {
        try (ZipInputStream zip = new ZipInputStream(new URL(url).openStream())) {
            if (zip.getNextEntry() == null) {
                return Collections.emptyList();
            }
            return ecbCsvParser.parse(zip).flatMap(this::convert).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Stream<ExchangeRate> convert(EcbRates rates) {
        return rates.getRates().entrySet().stream().map(e -> new ExchangeRate(
                null,
                getId(),
                rates.getOrigCurrency(),
                null,
                e.getKey(),
                e.getValue().setScale(6, RoundingMode.HALF_EVEN),
                rates.getDate(),
                null
        ));
    }
}
