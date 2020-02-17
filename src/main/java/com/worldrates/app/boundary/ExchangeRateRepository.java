package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import com.worldrates.app.entity.RestPage;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;

import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Singleton
@Transactional
public class ExchangeRateRepository  {
    public RestPage<ExchangeRate> findByDate(LocalDate localDate, Page page, Sort sort) {
        List<ExchangeRate> rates = ExchangeRate.find("date", sort, localDate)
                .page(page)
                .list();

        return new RestPage<>(rates, ExchangeRate.count());
    }

    public List<ExchangeRate> findByProviderIdAndDateIn(String providerId, Collection<LocalDate> dates) {
        return ExchangeRate.find("providerId = ?1 and date in (?2)", providerId, dates).list();
    }

    public void persist(Collection<ExchangeRate> rates) {
        rates.forEach(JpaOperations.getEntityManager()::merge);
    }

}
