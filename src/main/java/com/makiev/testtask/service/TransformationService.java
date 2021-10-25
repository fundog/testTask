package com.makiev.testtask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.makiev.testtask.dto.Dataset;
import org.springframework.stereotype.Service;

@Service
public class TransformationService {
    public Dataset transformXmlToDataset(String xmlBody){
        XmlMapper xmlMapper = new XmlMapper();
        Dataset convertedDatasetValue = null;

        try {
            convertedDatasetValue = xmlMapper.readValue(xmlBody, Dataset.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return convertedDatasetValue;
    }
}
