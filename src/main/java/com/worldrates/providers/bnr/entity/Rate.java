package com.worldrates.providers.bnr.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@Data
@XmlRootElement(name = "Rate")
@XmlAccessorType(XmlAccessType.FIELD)
public class Rate {
    @XmlValue
    private BigDecimal value;

    @XmlAttribute(name = "multiplier")
    private BigDecimal multiplier;

    @XmlAttribute(name = "currency")
    private String currency;
}
