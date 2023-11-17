package com.aws.devessentials.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BackendServices {

    @Value("${backend.retrieve.url}")
    private String backendRetrieveUrl;

    @Value("${backend.create.url}")
    private String backendCreateUrl;

    public String getBackendRetrieveUrl() {
        return backendRetrieveUrl;
    }

    public void setBackendRetrieveUrl(String backendRetrieveUrl) {
        this.backendRetrieveUrl = backendRetrieveUrl;
    }

    public String getBackendCreateUrl() {
        return backendCreateUrl;
    }

    public void setBackendCreateUrl(String backendCreateUrl) {
        this.backendCreateUrl = backendCreateUrl;
    }
}

