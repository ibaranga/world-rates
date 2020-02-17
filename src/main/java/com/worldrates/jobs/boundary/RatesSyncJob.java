package com.worldrates.jobs.boundary;

import com.worldrates.app.boundary.RatesProvider;
import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.jobs.control.RatesSync;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class RatesSyncJob {
    private static final Logger LOG = LoggerFactory.getLogger(RatesSyncJob.class);

    private final Instance<RatesProvider> providers;
    private final RatesSync ratesSync;
    private final String cron;

    @Inject
    public RatesSyncJob(
            Instance<RatesProvider> providers,
            RatesSync ratesSync,
            @ConfigProperty(name = "app.sync.cron", defaultValue = "-") String cron) {
        this.providers = providers;
        this.ratesSync = ratesSync;
        this.cron = cron;
    }

    @PostConstruct
    public void init() {
        LOG.info("Starting RatesSyncJob with {}", cron);
    }

    @Scheduled(cron = "{app.sync.cron}")
    public void syncRates() {
        providers.forEach(this::syncRates);
    }

    private void syncRates(RatesProvider ratesProvider) {
        LOG.info("Syncing {} ...", ratesProvider.getId());

        List<ExchangeRate> dailyRates = ratesProvider.getDailyRates();

        LOG.info("Got {} rates from {}. Now saving ...", dailyRates.size(), ratesProvider.getId());

        ratesSync.syncRates(ratesProvider.getId(), dailyRates);

        LOG.info("Saved {} rates from {}.", dailyRates.size(), ratesProvider.getId());
    }
}
