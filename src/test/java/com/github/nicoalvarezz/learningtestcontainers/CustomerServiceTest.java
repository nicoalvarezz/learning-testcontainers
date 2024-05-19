package com.github.nicoalvarezz.learningtestcontainers;

import com.github.nicoalvarezz.learningtestcontainers.service.CustomerService;
import com.github.nicoalvarezz.learningtestcontainers.db.DBConnectionProvider;
import com.github.nicoalvarezz.learningtestcontainers.db.dao.Customer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.UUID;

public class CustomerServiceTest {

    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    CustomerService customerService;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        DBConnectionProvider conn = DBConnectionProvider.getInstance();
        conn.setUrl(postgres.getJdbcUrl());
        conn.setUsername(postgres.getUsername());
        conn.setPassword(postgres.getPassword());
        customerService = new CustomerService();
    }

    @Test
    void getAllCustomersTest() {
        customerService.createCustomer(new Customer(UUID.randomUUID().toString(), "Alice"));
        customerService.createCustomer(new Customer(UUID.randomUUID().toString(), "John"));

        List<Customer> customers = customerService.getAllCustomers();
        Assertions.assertEquals(2, customers.size());
    }
}
