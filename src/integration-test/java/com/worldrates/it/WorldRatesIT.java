package com.worldrates.it;

import com.worldrates.ClientFactory;
import com.worldrates.app.boundary.WorldRatesClient;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RateSyncStatus;
import com.worldrates.app.entity.RestPage;
import io.quarkus.runtime.configuration.QuarkusConfigFactory;
import io.smallrye.config.SmallRyeConfigBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WorldRatesIT {
    private static final Logger LOG = LoggerFactory.getLogger(WorldRatesIT.class);
    WorldRatesClient client = new WorldRatesClient(
            System.getProperty("it.baseUrl", "http://localhost:8080"),
            ClientFactory.defaultClient());

    @BeforeAll
    public static void setItUp() {
        // TODO this workaround should no longer be needed in 1.3.0
        // https://github.com/quarkusio/quarkus/issues/6131#issuecomment-565496547c
        QuarkusConfigFactory.setConfig(new SmallRyeConfigBuilder()
                .addDefaultSources()
                .addDiscoveredConverters()
                .addDiscoveredSources()
                .build());
    }

    @Test
    public void shouldGetExchangeRatesAfterRunningSyncJob() {
        LocalDate lastWeekDay = getLastWeekDay();

        RestPage<ExchangeRate> ratesPage = client.rates().getAll(lastWeekDay, 0, 100);

        assertNotNull(ratesPage);

        LOG.info("Got {}", ratesPage);

        RateSyncStatus rateSyncStatus = client.admin().runRatesSync();

        LOG.info("Sync status {}", rateSyncStatus);

        assertNotNull(rateSyncStatus);

        assertEquals("OK", rateSyncStatus.getStatus());

        ratesPage = client.rates().getAll(lastWeekDay, 0, 100);

        assertNotNull(ratesPage);

        LOG.info("Got {}", ratesPage);
    }

    private LocalDate getLastWeekDay() {
        LocalDate lastWeekDay = LocalDate.now();
        while (lastWeekDay.getDayOfWeek().compareTo(DayOfWeek.FRIDAY) > 0) {
            lastWeekDay = lastWeekDay.minusDays(1);
        }
        return lastWeekDay;
    }
}
