package com.worldrates.providers.era.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class EraRates {

    private String base;
    private String date;
    private Long time_last_updated;
    private Map<String, BigDecimal> rates;

}
