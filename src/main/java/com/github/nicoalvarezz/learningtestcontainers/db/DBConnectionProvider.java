package com.github.nicoalvarezz.learningtestcontainers.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionProvider {

    private String url;
    private String username;
    private String password;
    private static DBConnectionProvider INSTANCE;

    private DBConnectionProvider() {}

    public static DBConnectionProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBConnectionProvider();
        }
        return INSTANCE;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
