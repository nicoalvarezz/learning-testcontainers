package com.github.nicoalvarezz.learningtestcontainers.resources;


import com.github.nicoalvarezz.learningtestcontainers.api.CustomerResponse;
import com.github.nicoalvarezz.learningtestcontainers.db.dao.Customer;
import com.github.nicoalvarezz.learningtestcontainers.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;


@Path("/api/v1/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final CustomerService customerService = new CustomerService();

    @GET
    @Path("register-customer")
    public Response registerCustomer(@Context HttpServletResponse response, @QueryParam("name") String name) throws URISyntaxException {
        customerService.createCustomer(new Customer(UUID.randomUUID().toString(), name));

        // Manipulate the response as needed
        String responseBody = "This is the response body\r\n";
        Response.ResponseBuilder responseBuilder = Response.status(201)
                .entity(responseBody)
                .header("transfer-encoding", "chunked")
                .header("Trailer", "key1");


        Supplier<Map<String, String>> supplier = () -> {
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            return map;
        };
        response.setTrailerFields(supplier);
        return responseBuilder.build();
    }

    @GET
    @Path("all-customers")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customer -> new CustomerResponse(customer.id(), customer.name()))
                .toList();
    }
}
