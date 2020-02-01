package com.worldrates.providers.bnr.entity;

import com.worldrates.providers.bnr.boundary.LocalDateAdapter;
import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.List;

@Data
@XmlRootElement(name = "Cube", namespace = "http://www.bnr.ro/xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cube {
    @XmlAttribute(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate date;
    @XmlElement(name = "Rate", namespace = "http://www.bnr.ro/xsd")
    private List<Rate> rates;
}
