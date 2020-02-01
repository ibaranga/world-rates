package com.worldrates.app.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ExchangeRateKey {
    private final String providerId;
    private final String currency;
    private final String origCurrency;
    private final LocalDate date;
}
