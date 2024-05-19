package com.github.nicoalvarezz.learningtestcontainers.service;

import com.github.nicoalvarezz.learningtestcontainers.db.dao.Customer;
import com.github.nicoalvarezz.learningtestcontainers.db.DBConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private final DBConnectionProvider connectionProvider = DBConnectionProvider.getInstance();

    public CustomerService() {
        createCustomersTableIfNotExists();
    }

    public void createCustomer(Customer customer) {
        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "insert into customers(id, name) values(?, ?)"
            );
            statement.setString(1, customer.id());
            statement.setString(2, customer.name());
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try (Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    "select id, name from customers"
            );
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                customers.add(new Customer(result.getString("id"), result.getString("name")));
            }
            return customers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomersTableIfNotExists() {
        try(Connection conn = this.connectionProvider.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(
                    """
                        create table if not exists customers (
                            id varchar not null,
                            name varchar not null,
                            primary key(id)
                        )
                        """
            );
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
