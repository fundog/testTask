package com.makiev.testtask.repository;

import com.makiev.testtask.domain.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
    List<Resource> findAllByResourceUrl(String resourceUrl);
    Resource findResourceById(Long id);
}
