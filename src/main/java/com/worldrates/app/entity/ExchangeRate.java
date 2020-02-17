package com.worldrates.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@Table(name = "exchange_rate")
public class ExchangeRate extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "provider_id")
    private String providerId;

    @NotNull
    @NotEmpty
    @Column(name = "orig_currency")
    private String origCurrency;

    @Column(name = "multiplier")
    private BigDecimal multiplier;

    @Column(name = "currency")
    @NotNull
    @NotEmpty
    private String currency;

    @Column(precision = 12, scale = 6, name = "rate")
    @NotNull
    private BigDecimal rate;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @JsonIgnore
    @Transient
    public ExchangeRateKey getKey() {
        return new ExchangeRateKey(providerId, currency, origCurrency, date);
    }

}
