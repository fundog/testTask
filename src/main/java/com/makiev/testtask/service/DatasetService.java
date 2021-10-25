package com.makiev.testtask.service;

import com.makiev.testtask.configuration.Statuses;
import com.makiev.testtask.domain.Email;
import com.makiev.testtask.domain.Resource;
import com.makiev.testtask.dto.Dataset;
import com.makiev.testtask.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:customProperties.properties")
@Service
public class DatasetService {
    @Autowired
    ResourceRepository repository;
    @Value("${allowedDomains}")
    String allowedDomainsString;
    private static final String EMAIL_DOMAIN_DELIMITER = "@";

    public void insertResourcesFromRequestDataset(Dataset dataset, String resourceUrl){
        Resource resource = createResource(resourceUrl, Statuses.COMPLETED);
        updateResourcesFromDataset(dataset, resource);
    }

    public void updateResourcesFromDataset(Dataset dataset, Resource resource){
        resource.setStatus(Statuses.COMPLETED.name());
        addEmailsAndSetOnToResource(dataset, resource);
        repository.save(resource);
        createResourcesForJobRequest(dataset.getResources());
    }

    private void createResourcesForJobRequest(List<String> resourceUrls){
        if(resourceUrls != null && !resourceUrls.isEmpty()) {
            for (String resourceUrl : resourceUrls) {
                Resource resource = createResource(resourceUrl, Statuses.WAITING_SENT);
                repository.save(resource);
            }
        }
    }

    private void addEmailsAndSetOnToResource (Dataset dataset, Resource newResource){
        newResource.setSentOn(new Date());
        List<Email> emails = emailsForResource(dataset, newResource);
        newResource.setEmails(emails);
    }

    private List<Email> emailsForResource(Dataset dataset, Resource resource){
        List<String> validEmails = filterInvalidEmails(dataset.getEmails());
        List<Email> emails = null;

        if(validEmails != null && !validEmails.isEmpty()){
            emails = new ArrayList<>();

            for(String email: validEmails){
                emails.add(new Email(email, resource));
            }
        }

        return emails;
    }

    private Resource createResource(String resourceUrl, Statuses status){
        Date currentDate = new Date();
        return new Resource(resourceUrl, currentDate, null, status);
    }

    private List<String> filterInvalidEmails(List<String> emails){
        List<String> allowedDomains = getAllowedDomains();

        return emails.stream().
                filter(email -> allowedDomains.contains(email.substring(email.lastIndexOf(EMAIL_DOMAIN_DELIMITER) + 1)))
                .collect(Collectors.toList());
    }

    public List<String> getAllowedDomains(){
        return Arrays.asList(this.allowedDomainsString.split(","));
    }
}
