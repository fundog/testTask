package com.makiev.testtask.repository;

import com.makiev.testtask.domain.Email;
import com.makiev.testtask.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findAllByEmail(String resourceUrl);
    List<Email> findAllByResource(Resource resource);
    Long countByEmail(String email);
    @Query("SELECT DISTINCT count(e.email) FROM Email e")
    Long countDistinctEmail();
    @Transactional
    @Modifying
    @Query("DELETE FROM Email e WHERE e.resource.id =:#{#id} ")
    void deleteAllByIdResourceId(@Param("id")Long id);
    @Query("SELECT count(e) FROM Email e WHERE e.resource.sentOn > :#{#date} and e.email = :#{#email}")
    Long countEmailFromLast5MinutesBulk(@Param("date") Date date, @Param("email") String email);
}
