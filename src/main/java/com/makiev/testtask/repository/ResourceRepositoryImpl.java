package com.makiev.testtask.repository;

import com.makiev.testtask.domain.Resource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ResourceRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Resource> getResourcesForBulk(Integer limit, List<String> statuses) {
        String queryString = "SELECT r FROM Resource r where r.status IN (:statusesForSend) ORDER BY r.createdOn";
        Query query = entityManager.createQuery(queryString, Resource.class);
        query.setParameter("statusesForSend", statuses);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
