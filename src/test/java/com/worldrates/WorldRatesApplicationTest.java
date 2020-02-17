package com.worldrates;

import com.worldrates.app.boundary.ExchangeRateRepository;
import com.worldrates.app.boundary.WorldRatesClient;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RestPage;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.net.ServerSocket;
import java.net.URL;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class WorldRatesApplicationTest {

    @TestHTTPResource
    URL url;

    @Inject
    ExchangeRateRepository repository;

    @BeforeAll
    public static void setup() throws Exception {
        if (System.getProperty("server.port") == null) {
            System.setProperty("server.port", String.valueOf(new ServerSocket(0).getLocalPort()));
        }
    }


    @Test
    void shouldSyncRatesAndNotDuplicateThem() {
        WorldRatesClient worldRatesClient = new WorldRatesClient(url.toString(), ClientFactory.defaultClient());

        LocalDate date = LocalDate.of(2020, 1, 31);

        worldRatesClient.admin().runRatesSync();

        RestPage<ExchangeRate> page1 = worldRatesClient.rates().getAll(date, 0, 50);
        RestPage<ExchangeRate> page2 = worldRatesClient.rates().getAll(date, 1, 50);
        RestPage<ExchangeRate> page3 = worldRatesClient.rates().getAll(date, 2, 50);

        assertNotNull(page1);
        assertNotNull(page2);
        assertNotNull(page3);

        assertEquals(115, page1.getTotalElements());
        assertEquals(50, page1.getContent().size());

        assertEquals(115, page2.getTotalElements());
        assertEquals(50, page2.getContent().size());

        assertEquals(115, page3.getTotalElements());
        assertEquals(15, page3.getContent().size());

        worldRatesClient.admin().runRatesSync();

        page1 = worldRatesClient.rates().getAll(date, 0, 50);
        page2 = worldRatesClient.rates().getAll(date, 1, 50);
        page3 = worldRatesClient.rates().getAll(date, 2, 50);

        assertNotNull(page1);
        assertNotNull(page2);
        assertNotNull(page3);

        assertEquals(115, page1.getTotalElements());
        assertEquals(50, page1.getContent().size());

        assertEquals(115, page2.getTotalElements());
        assertEquals(50, page2.getContent().size());

        assertEquals(115, page3.getTotalElements());
        assertEquals(15, page3.getContent().size());

    }


}
