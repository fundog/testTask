package com.makiev.testtask.service;

import com.makiev.testtask.dto.Dataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FetchingDataService {
    RestTemplate restTemplate;
    TransformationService transformationService;

    @Autowired
    public FetchingDataService(RestTemplate restTemplate, TransformationService transformationService){
        this.transformationService = transformationService;
        this.restTemplate = restTemplate;
    }

    public Dataset fetchDataFromExternalUrl(String url){
        ResponseEntity resp = restTemplate.getForEntity(url, Dataset.class);
        Dataset result = null;

        if(resp.getStatusCode() == HttpStatus.OK){
            if(resp.getBody() instanceof Dataset){
                result = (Dataset) resp.getBody();
            } else {
                result = transformationService.transformXmlToDataset(resp.getBody().toString());
            }
        }

        return result;
    }
}
