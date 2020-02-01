package com.worldrates.app.boundary;

import com.worldrates.app.entity.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    Page<ExchangeRate> findByDate(LocalDate localDate, Pageable pageable);

    List<ExchangeRate> findByProviderIdAndDateIn(String providerId, Collection<LocalDate> dates);
}
