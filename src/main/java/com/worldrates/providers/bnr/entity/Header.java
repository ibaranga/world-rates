package com.worldrates.providers.bnr.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@Data
@XmlRootElement(name = "Header", namespace = "http://www.bnr.ro/xsd")
@XmlAccessorType(XmlAccessType.FIELD)
public class Header {
    @XmlElement(name = "Publisher", namespace = "http://www.bnr.ro/xsd")
    private Publisher publisher;
    @XmlElement(name = "PublishingDate", namespace = "http://www.bnr.ro/xsd")
    private PublishingDate publishingDate;
    @XmlElement(name = "MessageType", namespace = "http://www.bnr.ro/xsd")
    private MessageType messageType;

}
