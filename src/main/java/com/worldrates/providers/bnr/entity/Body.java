package com.worldrates.providers.bnr.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "Body")
@XmlAccessorType(XmlAccessType.FIELD)
public class Body {
    @XmlElement(name = "Subject", namespace = "http://www.bnr.ro/xsd")
    private Subject subject;
    @XmlElement(name = "OrigCurrency", namespace = "http://www.bnr.ro/xsd")
    private OrigCurrency origCurrency;
    @XmlElement(name = "Cube", namespace = "http://www.bnr.ro/xsd")
    private List<Cube> cube;
}
