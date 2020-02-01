package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
public class ExchangeRateController {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateController(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GetMapping("/rates")
    public Page<ExchangeRate> getRates(
            @RequestParam(value = "date", required = false) @DateTimeFormat(iso = DATE) LocalDate date,
            @RequestParam(value = "page", required = false, defaultValue =  "0") @Min(0) int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") @Min(0) @Max(200) int size
    ) {

        if (date == null) {
            date = LocalDate.now();
        }

        return exchangeRateRepository.findByDate(date, PageRequest.of(page, size, Sort.by("createdAt")));
    }
}
