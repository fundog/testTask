package com.makiev.testtask.controller;

import com.makiev.testtask.domain.Email;
import com.makiev.testtask.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
public class EmailController {
    @Autowired
    EmailRepository repository;

    @GetMapping("/email/times/{email}")
    public Long howManyTimesEmailMet(@PathVariable String email){
        return repository.countByEmail(email);
    }

    @GetMapping("/email/count")
    public Long countEmail(){
        return repository.count();
    }

    @GetMapping("/email/count/unique")
    public Long countUniqueEmails(){
        return repository.countDistinctEmail();
    }

    @PostMapping("/email/")
    public Email newEmail(@RequestBody Email newEmail) {
        return repository.save(newEmail);
    }

    @DeleteMapping("/email/{id}")
    public void deleteEmail(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @DeleteMapping("/email/resource/{id}")
    public void deleteEmailsForResource(@PathVariable Long id) {
        repository.deleteAllByIdResourceId(id);
    }

    @GetMapping("/email/count/last5/{email}")
    public Long countEmailFromLast5MinutesBulk(@PathVariable String email){
        Date date = new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(100));
        return repository.countEmailFromLast5MinutesBulk(date, email);
    }
}
