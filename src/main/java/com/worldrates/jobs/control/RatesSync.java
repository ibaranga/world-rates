package com.worldrates.jobs.control;

import com.worldrates.app.boundary.ExchangeRateRepository;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.ExchangeRateKey;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class RatesSync {
    private final ExchangeRateRepository exchangeRateRepository;

    public RatesSync(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public void syncRates(String providerId, List<ExchangeRate> exchangeRates) {
        Collection<LocalDate> dates = exchangeRates.stream().map(ExchangeRate::getDate).collect(Collectors.toSet());

        Map<ExchangeRateKey, ExchangeRate> existingRates = exchangeRateRepository.findByProviderIdAndDateIn(providerId, dates)
                .stream()
                .collect(Collectors.toMap(ExchangeRate::getKey, Function.identity()));

        exchangeRates.forEach(exchangeRate -> {
            ExchangeRate existingRate = existingRates.get(exchangeRate.getKey());
            if (existingRate != null) {
                exchangeRate.setId(existingRate.getId());
                exchangeRate.setCreatedAt(existingRate.getCreatedAt());
            }
        });

        exchangeRateRepository.saveAll(exchangeRates);
    }
}
