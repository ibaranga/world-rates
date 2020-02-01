package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;

import java.util.List;

public interface RatesProvider {
    String getId();
    List<ExchangeRate> getDailyRates();
}
