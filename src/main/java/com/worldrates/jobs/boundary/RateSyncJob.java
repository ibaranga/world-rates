package com.worldrates.jobs.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.jobs.control.RatesSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RateSyncJob {
    private static final Logger LOG = LoggerFactory.getLogger(RateSyncJob.class);

    private final List<RatesProvider> providers;
    private final RatesSync ratesSync;

    public RateSyncJob(List<RatesProvider> providers, RatesSync ratesSync) {
        this.providers = providers;
        this.ratesSync = ratesSync;
    }

    @Scheduled(cron = "${app.sync.cron}")
    public void syncRates() {
        providers.forEach(this::syncRates);
    }

    private void syncRates(RatesProvider ratesProvider) {
        LOG.info("Syncing {} ...", ratesProvider.getId());

        List<ExchangeRate> dailyRates = ratesProvider.getDailyRates();

        LOG.info("Got {} rates from {}. Now saving ...", dailyRates.size(), ratesProvider.getId());

        ratesSync.sync(ratesProvider.getId(), dailyRates);

        LOG.info("Saved {} rates from {}.", dailyRates.size(), ratesProvider.getId());
    }
}
