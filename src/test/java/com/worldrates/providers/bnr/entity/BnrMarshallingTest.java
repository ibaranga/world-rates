package com.worldrates.providers.bnr.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class BnrMarshallingTest {
    @Test
    void testMarshallAndUnmarshall() throws Exception {
        Unmarshaller unmarshaller = JAXBContext.newInstance(DataSet.class).createUnmarshaller();
        Marshaller marshaller = JAXBContext.newInstance(DataSet.class).createMarshaller();

        DataSet dataSet = (DataSet) unmarshaller.unmarshal(new ClassPathResource("static/bnr-rates.xml").getURL());

        StringWriter sw = new StringWriter();
        marshaller.marshal(dataSet, sw);
        DataSet dataSet2 = (DataSet) unmarshaller.unmarshal(new StringReader(sw.toString()));

        Assertions.assertEquals(dataSet, dataSet2);

    }
}
