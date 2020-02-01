package com.worldrates.providers.ecb.control;

import com.worldrates.providers.ecb.entity.EcbRates;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EcbCsvParserTest {

    @Test
    void parse() throws Exception {
        String csv = "Date, USD, JPY, BGN, CZK, DKK, GBP, HUF, PLN, RON, SEK, CHF, ISK, NOK, HRK, RUB, TRY, AUD, BRL, CAD, CNY, HKD, IDR, ILS, INR, KRW, MXN, MYR, NZD, PHP, SGD, THB, ZAR, \n" +
                "31 January 2020, 1.1052, 120.35, 1.9558, 25.210, 7.4731, 0.84175, 337.05, 4.3009, 4.7789, 10.6768, 1.0694, 136.30, 10.1893, 7.4440, 70.3375, 6.6117, 1.6494, 4.7157, 1.4627, 7.6664, 8.5843, 15091.51, 3.8090, 78.9055, 1321.60, 20.8044, 4.5297, 1.7083, 56.382, 1.5092, 34.460, 16.4900, \n";

        List<EcbRates> ratesList = EcbCsvParser.defaultParser()
                .parse(new ByteArrayInputStream(csv.getBytes()))
                .collect(Collectors.toList());

        assertEquals(1, ratesList.size());

        EcbRates rates = ratesList.get(0);

        assertEquals(LocalDate.of(2020, 1, 31), rates.getDate());

        assertEquals(new BigDecimal("1.1052"), rates.getRates().get("USD"));


    }

}
