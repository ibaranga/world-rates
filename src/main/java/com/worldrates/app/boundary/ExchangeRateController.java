package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RestPage;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.*;
import java.time.LocalDate;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Path("/")
public class ExchangeRateController {
    private final ExchangeRateRepository exchangeRateRepository;

    @Inject
    public ExchangeRateController(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GET
    @Path("/rates")
    public RestPage<ExchangeRate> getRates(
            @QueryParam(value = "date") LocalDate date,
            @QueryParam(value = "page") @DefaultValue("1") @Min(0) int page,
            @QueryParam(value = "size") @DefaultValue("10")  @Min(0) @Max(200) int size
    ) {

        if (date == null) {
            date = LocalDate.now();
        }

        return exchangeRateRepository.findByDate(date, Page.of(page, size), Sort.ascending("createdAt"));
    }
}
