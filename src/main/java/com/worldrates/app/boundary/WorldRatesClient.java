package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RateSyncStatus;
import com.worldrates.app.entity.RestPage;
import lombok.AllArgsConstructor;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import java.time.LocalDate;

import static java.lang.String.format;

public class WorldRatesClient {
    private final Rates rates;
    private final Admin admin;

    public WorldRatesClient(String baseUrl, Client client) {
        this.rates = new Rates(baseUrl, client);
        this.admin = new Admin(baseUrl, client);
    }

    public WorldRatesClient(Client client) {
        this("", client);
    }

    public Rates rates() {
        return rates;
    }

    public Admin admin() {
        return admin;
    }

    public static class Rates extends ClientBase {
        private final GenericType<RestPage<ExchangeRate>> pageTypeRef = new GenericType<RestPage<ExchangeRate>>() {
        };

        public Rates(String baseUrl, Client client) {
            super(baseUrl, client);
        }

        public RestPage<ExchangeRate> getAll(LocalDate date, int page, int size) {
            String url = format("%s/rates?date=%s&page=%s&size=%s", baseUrl, date.toString(), page, size);
            return client.target(url).request().get(pageTypeRef);
        }
    }

    public static class Admin extends ClientBase {
        public Admin(String baseUrl, Client client) {
            super(baseUrl, client);
        }

        public RateSyncStatus runRatesSync() {
            String url = format("%s/admin/jobs/dailyRatesSync", baseUrl);
            return client.target(url).request().post(null, RateSyncStatus.class);
        }
    }

    @AllArgsConstructor
    private static class ClientBase {
        final String baseUrl;
        final Client client;
    }
}
