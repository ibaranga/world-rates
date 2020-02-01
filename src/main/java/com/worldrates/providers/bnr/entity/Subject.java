package com.worldrates.providers.bnr.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlRootElement(name = "Subject")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subject {
    @XmlValue
    private String value;
}
