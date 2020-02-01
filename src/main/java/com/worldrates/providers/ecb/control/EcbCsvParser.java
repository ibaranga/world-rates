package com.worldrates.providers.ecb.control;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.worldrates.providers.ecb.entity.EcbRates;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.zip.ZipInputStream;

public class EcbCsvParser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    private static final EcbCsvParser DEFAULT_PARSER = new EcbCsvParser(
            "EUR",
            List.of("USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK", "CHF", "ISK", "NOK",
                    "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW", "MXN",
                    "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"));


    private final String origCurrency;
    private final List<String> currencies;

    private final CsvMapper mapper;
    private final CsvSchema schema;
    private final DateTimeFormatter dateTimeFormatter;

    public EcbCsvParser(String origCurrency, List<String> currencies) {
        this(origCurrency, currencies, new CsvMapper(), CsvSchema.emptySchema().withHeader(), DATE_TIME_FORMATTER);
    }

    public EcbCsvParser(String origCurrency, List<String> currencies, CsvMapper mapper, CsvSchema schema, DateTimeFormatter dateTimeFormatter) {
        this.origCurrency = origCurrency;
        this.currencies = currencies;
        this.mapper = mapper;
        this.schema = schema;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    public Stream<EcbRates> parse(InputStream inputStream) throws IOException {
        MappingIterator<Map<String, String>> iterator = mapper.readerFor(Map.class)
                .with(schema)
                .withFeatures(CsvParser.Feature.TRIM_SPACES)
                .readValues(inputStream);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false)
                .map(this::parse);
    }

    private EcbRates parse(Map<String, String> values) {
        LocalDate date = LocalDate.parse(values.get("Date"), dateTimeFormatter);

        Map<String, BigDecimal> rates = currencies.stream()
                .filter(values::containsKey)
                .collect(Collectors.toMap(Function.identity(), currency -> {
                    try {
                        return new BigDecimal(values.get(currency));
                    } catch (Exception e) {
                        System.out.println(currency + ", " + values.get(currency));
                        return BigDecimal.ONE;
                    }
                }));

        return new EcbRates(date, origCurrency, rates);

    }

    public static EcbCsvParser defaultParser() {
        return DEFAULT_PARSER;
    }


}
