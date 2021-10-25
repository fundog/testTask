package com.makiev.testtask.service;

import com.makiev.testtask.configuration.Statuses;
import com.makiev.testtask.domain.Resource;
import com.makiev.testtask.dto.Dataset;
import com.makiev.testtask.repository.ResourceRepository;
import com.makiev.testtask.repository.ResourceRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@PropertySource("classpath:customProperties.properties")
@Component
public class TaskSchedulerService {

    @Autowired
    ResourceRepositoryImpl resourceRepository;
    @Autowired
    FetchingDataService fetchingDataService;
    @Autowired
    DatasetService datasetService;
    @Autowired
    ResourceRepository repository;

    @Value("${numberOfBulkThreads}")
    private Integer numberOfBulkThreads;
    private ExecutorService executorService;

    private static final List<String> STATUSES_FOR_SEND_BULK_RESOURCE = Arrays.asList(
            Statuses.WAITING_SENT.name(),
            Statuses.ERROR.name()
    );

    /**
     * It's scheduled once per 5 minutes, can be changed
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void runSendingResources(){
        executorService = Executors.newFixedThreadPool(numberOfBulkThreads);
        List<Resource> resourcesForBulkSend = resourceRepository.getResourcesForBulk(numberOfBulkThreads, STATUSES_FOR_SEND_BULK_RESOURCE);

        if (resourcesForBulkSend.size() > 0) {
            for (Resource resource : resourcesForBulkSend) {
                executorService.submit(() -> {
                    try {
                        Dataset dataset = fetchingDataService.fetchDataFromExternalUrl(resource.getResourceUrl());
                        datasetService.updateResourcesFromDataset(dataset, resource);
                    } catch (Exception e) {
                        resource.setStatus(Statuses.ERROR.name());
                        repository.save(resource);
                        Thread.currentThread().interrupt();
                    }
                });
            }

            executorService.shutdown();

            try {
                //termination almost equal schedule time to finish all threads if they do not get response yet
                executorService.awaitTermination(4, TimeUnit.MINUTES);
            } catch (InterruptedException e) {

            }
        }
    }
}
