package com.worldrates.providers.ecb.control;

import com.worldrates.providers.ecb.entity.EcbRates;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EcbCsvParserTest {

    @Test
    void parse() throws Exception {
        String csv = "Date,            USD,    JPY,    BGN,    \n" +
                "     31 January 2020, 1.1052, 120.35, 1.9558, \n";

        List<EcbRates> ratesList;

        try (InputStream is = new ByteArrayInputStream(csv.getBytes())) {
            ratesList = EcbCsvParser.defaultParser().parse(is).collect(Collectors.toList());
        }

        assertEquals(1, ratesList.size());

        EcbRates rates = ratesList.get(0);

        assertEquals(LocalDate.of(2020, 1, 31), rates.getDate());

        Map<String, BigDecimal> ratesValues = rates.getValues();

        assertEquals(3, ratesValues.size());
        assertEquals(new BigDecimal("1.1052"), ratesValues.get("USD"));
        assertEquals(new BigDecimal("120.35"), ratesValues.get("JPY"));
        assertEquals(new BigDecimal("1.9558"), ratesValues.get("BGN"));


    }

}
