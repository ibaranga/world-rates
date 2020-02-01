package com.worldrates;

import com.worldrates.jobs.boundary.RatesSyncJob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.net.ServerSocket;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class WorldRatesApplicationIT {
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
        ratesSyncJob.syncRates();
        Map<String, Object> page1 = testRestTemplate.getForObject("/rates?date=2020-01-31&page=0&size=50", Map.class);
        Map<String, Object> page2 = testRestTemplate.getForObject("/rates?date=2020-01-31&page=1&size=50", Map.class);

        Assertions.assertEquals(64, (Integer) page1.get("totalElements"));
        Assertions.assertEquals(64, (Integer) page2.get("totalElements"));

        ratesSyncJob.syncRates();
        page1 = testRestTemplate.getForObject("/rates?date=2020-01-31&page=0&size=50", Map.class);
        page2 = testRestTemplate.getForObject("/rates?date=2020-01-31&page=1&size=50", Map.class);

        Assertions.assertEquals(64, (Integer) page1.get("totalElements"));
        Assertions.assertEquals(64, (Integer) page2.get("totalElements"));
    }


}
