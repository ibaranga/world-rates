package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RateSyncStatus;
import com.worldrates.app.entity.RestPage;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.GET;

public class WorldRatesClient {
    private final Rates rates;
    private final Admin admin;

    public WorldRatesClient(String baseUrl, RestTemplate rest) {
        this.rates = new Rates(baseUrl, rest);
        this.admin = new Admin(baseUrl, rest);
    }

    public WorldRatesClient(RestTemplate rest) {
        this("", rest);
    }

    public Rates rates() {
        return rates;
    }

    public Admin admin() {
        return admin;
    }

    public static class Rates extends ClientBase {
        private final ParameterizedTypeReference<RestPage<ExchangeRate>> pageTypeRef = new ParameterizedTypeReference<>() {
        };

        public Rates(String baseUrl, RestTemplate rest) {
            super(baseUrl, rest);
        }

        public RestPage<ExchangeRate> getAll(LocalDate date, int page, int size) {
            String url = format("%s/rates?date=%s&page=%s&size=%s", baseUrl, date.toString(), page, size);
            return rest.exchange(url, GET, null, pageTypeRef).getBody();
        }
    }

    public static class Admin extends ClientBase {
        public Admin(String baseUrl, RestTemplate rest) {
            super(baseUrl, rest);
        }

        public RateSyncStatus runRatesSync() {
            String url = format("%s/admin/jobs/dailyRatesSync", baseUrl);
            return rest.postForObject(url, null, RateSyncStatus.class);
        }
    }

    @AllArgsConstructor
    private static class ClientBase {
        final String baseUrl;
        final RestTemplate rest;
    }
}
