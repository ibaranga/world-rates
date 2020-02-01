package com.worldrates.providers.bnr.entity;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "DataSet", namespace = "http://www.bnr.ro/xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataSet {
    @XmlElement(name = "Header", namespace = "http://www.bnr.ro/xsd")
    private Header header;
    @XmlElement(name = "Body", namespace = "http://www.bnr.ro/xsd")
    private Body body;
}
