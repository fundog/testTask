package com.makiev.testtask.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for incoming data
 */
@JacksonXmlRootElement
public class Dataset implements Serializable {
    @JacksonXmlElementWrapper(localName = "emails")
    private List<String> emails = new ArrayList<>();
    @JacksonXmlElementWrapper(localName = "resources")
    private List<String> resources = new ArrayList<>();

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }
}
