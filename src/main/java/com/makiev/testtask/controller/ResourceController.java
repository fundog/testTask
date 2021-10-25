package com.makiev.testtask.controller;

import com.makiev.testtask.domain.Resource;
import com.makiev.testtask.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResourceController {
    @Autowired
    ResourceRepository repository;

    @GetMapping("/resource")
    List<Resource> getResources(){
        Pageable limit = PageRequest.of(0,50);// hardcoded now, can be adjusted by values from reques
        return (List<Resource>) repository.findAll(limit).getContent();
    }

    @GetMapping("/resource/{id}")
    Resource one(@PathVariable Long id) {
        return repository.findResourceById(id);
    }

    @PostMapping("/resource/")
    Resource newResource(@RequestBody Resource newResource) {
        return repository.save(newResource);
    }

    @DeleteMapping("/resource/{id}")
    void deleteResource(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
