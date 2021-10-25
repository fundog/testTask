package com.makiev.testtask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@TestPropertySource("/customProperties.properties")
public class FilePropertyInjectionUnitTest {
    @Value("${domains}")
    private String allowedDomains;

    @Test
    public void whenFilePropertyProvided_thenProperlyInjected() {
        assertTrue(Arrays.asList(allowedDomains.split(",")).size() == 2);
    }
}
