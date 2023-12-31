package com.aws.devessentials.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DbCredentials {

    @Value("${rds.dbinstance.host}")
    private String host;

    @Value("${rds.dbinstance.username}")
    private String username;

    @Value("${rds.dbinstance.password}")
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

