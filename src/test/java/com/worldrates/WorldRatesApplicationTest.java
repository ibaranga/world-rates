package com.worldrates;

import com.worldrates.app.boundary.WorldRatesClient;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RestPage;
import com.worldrates.jobs.boundary.RatesSyncJob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.ServerSocket;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WorldRatesApplicationTest {

    @Autowired
    RatesSyncJob ratesSyncJob;
    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeAll
    public static void setup() throws Exception {
        if (System.getProperty("server.port") == null) {
            System.setProperty("server.port", String.valueOf(new ServerSocket(0).getLocalPort()));
        }
    }

    @Test
    void shouldSyncRatesAndNotDuplicateThem() {
        LocalDate date = LocalDate.of(2020, 1, 31);
        WorldRatesClient client = new WorldRatesClient(testRestTemplate.getRestTemplate());

        ratesSyncJob.syncRates();

        RestPage<ExchangeRate> page1 = client.rates().getAll(date, 0, 50);
        RestPage<ExchangeRate> page2 = client.rates().getAll(date, 1, 50);

        assertNotNull(page1);
        assertNotNull(page2);

        assertEquals(64, page1.getTotalElements());
        assertEquals(50, page1.getContent().size());

        assertEquals(64, page2.getTotalElements());
        assertEquals(14, page2.getContent().size());

        ratesSyncJob.syncRates();

        page1 = client.rates().getAll(date, 0, 50);
        page2 = client.rates().getAll(date, 1, 50);

        assertNotNull(page1);
        assertNotNull(page2);

        assertEquals(64, page1.getTotalElements());
        assertEquals(50, page1.getContent().size());

        assertEquals(64, page2.getTotalElements());
        assertEquals(14, page2.getContent().size());

    }


}
