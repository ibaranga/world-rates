package com.worldrates.providers.bnr.entity;

import com.worldrates.providers.bnr.boundary.LocalDateAdapter;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@XmlRootElement(name = "PublishingDate")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublishingDate {
    @XmlValue
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate value;
}
