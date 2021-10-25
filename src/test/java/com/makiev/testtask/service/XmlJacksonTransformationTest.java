package com.makiev.testtask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.makiev.testtask.dto.Dataset;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XmlJacksonTransformationTest {
    @Test
    public void transform() throws JsonProcessingException{
        StringBuilder xmlContent = new StringBuilder();
        xmlContent.append("<dataset>")
                        .append("<emails>")
                            .append("<email>email1</email>")
                            .append("<email>email2</email>")
                            .append("<email>email3</email>")
                        .append("</emails>")
                        .append("<resources>")
                            .append("<resource>resource1</resource>")
                            .append("<resource>resource2</resource>")
                        .append("</resources>")
                   .append("</dataset>");

        XmlMapper xmlMapper = new XmlMapper();
        assertNotNull(xmlMapper);

        Dataset value = xmlMapper.readValue(xmlContent.toString(), Dataset.class);

        assertTrue(value.getEmails().size() == 3);
        assertTrue(value.getResources().size() == 2);
        assertTrue(value.getResources().get(0).equals("resource1"));
        assertTrue(value.getEmails().get(2).equals("email3"));
    }
}
