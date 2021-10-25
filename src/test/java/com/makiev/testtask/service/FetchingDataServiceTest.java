package com.makiev.testtask.service;

import com.makiev.testtask.dto.Dataset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class FetchingDataServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FetchingDataService service = new FetchingDataService(restTemplate, new TransformationService());

    @Test
    public void fetchDataFromExternalUrl(){
        String url = "http://localhost:8080/getdata";
        Mockito
                .when(restTemplate.getForEntity(url, Dataset.class))
                .thenReturn(new ResponseEntity(createXml(), HttpStatus.OK));

        Dataset dataset = service.fetchDataFromExternalUrl(url);
        assertTrue(dataset.getEmails().size() == 3);
        assertTrue(dataset.getResources().size() == 2);
        assertTrue(dataset.getResources().get(0).equals("resource1"));
        assertTrue(dataset.getEmails().get(2).equals("email3"));
    }

    private static String createXml() {
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

        return xmlContent.toString();
    }
}
