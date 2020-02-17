package com.worldrates.jobs.control;

import com.worldrates.app.boundary.ExchangeRateRepository;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.ExchangeRateKey;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class RatesSync {
    private final ExchangeRateRepository exchangeRateRepository;

    @Inject
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

        exchangeRateRepository.persist(exchangeRates);
    }
}
