package com.worldrates.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String providerId;

    @Column
    @NotNull
    @NotEmpty
    private String origCurrency;

    @Column
    private BigDecimal multiplier;

    @Column
    @NotNull
    @NotEmpty
    private String currency;

    @Column(precision = 12, scale = 6)
    @NotNull
    private BigDecimal rate;

    @Column
    @NotNull
    private LocalDate date;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Transient
    public ExchangeRateKey getKey() {
        return new ExchangeRateKey(providerId, currency, origCurrency, date);
    }

}
