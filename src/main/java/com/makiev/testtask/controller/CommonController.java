package com.makiev.testtask.controller;

import com.makiev.testtask.dto.Dataset;
import com.makiev.testtask.service.DatasetService;
import com.makiev.testtask.service.FetchingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class CommonController {

    @Autowired
    DatasetService datasetService;
    @Autowired
    FetchingDataService fetchingDataService;

    @PostMapping("/import")
    public void receiveXmldata(@RequestBody Dataset incomeData, HttpServletRequest request){
        datasetService.insertResourcesFromRequestDataset(incomeData, request.getRequestURI());
    }
}
