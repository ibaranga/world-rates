package com.worldrates.providers.ecb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public class EcbRates {
    private LocalDate date;
    private String origCurrency;
    private Map<String, BigDecimal> rates;
}
