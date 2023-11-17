package com.aws.devessentials.dao;

import com.aws.devessentials.model.DbCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionFactory {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    static Connection getConnection(final DbCredentials dbCredentials) {
        try {
            String host = dbCredentials.getHost() + "?useSSL=false";
            Class.forName(DRIVER_NAME);
            return DriverManager.getConnection(host, dbCredentials.getUsername(),dbCredentials.getPassword());
        } catch (ClassNotFoundException | SQLException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
