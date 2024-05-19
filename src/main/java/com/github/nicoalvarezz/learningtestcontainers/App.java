package com.github.nicoalvarezz.learningtestcontainers;

import com.github.nicoalvarezz.learningtestcontainers.configuration.CustomerConfig;
import com.github.nicoalvarezz.learningtestcontainers.db.DBConnectionProvider;
import com.github.nicoalvarezz.learningtestcontainers.filters.TrailersFilter;
import com.github.nicoalvarezz.learningtestcontainers.resources.CustomerResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;

import java.util.EnumSet;


public class App extends Application<CustomerConfig> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(CustomerConfig customerConfig, Environment environment) {
        DBConnectionProvider conn = DBConnectionProvider.getInstance();
        conn.setUrl("jdbc:postgresql://db:5432/testdb");
        conn.setUsername("test");
        conn.setPassword("test");
        CustomerResource resource = new CustomerResource();
        environment.jersey().register(resource);

//        environment.servlets().addFilter("TrailersFilter", new TrailersFilter())
//                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
    }
}
