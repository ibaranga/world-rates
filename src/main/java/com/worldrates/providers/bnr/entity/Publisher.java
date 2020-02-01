package com.worldrates.providers.bnr.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@Data
@XmlRootElement(name = "Publisher")
@XmlAccessorType(XmlAccessType.FIELD)
public class Publisher {
    @XmlValue
    private String value;
}
