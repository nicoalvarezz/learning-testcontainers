    package com.github.nicoalvarezz.learningtestcontainers;

    import com.github.nicoalvarezz.learningtestcontainers.db.DBConnectionProvider;
    import com.github.nicoalvarezz.learningtestcontainers.db.dao.Customer;
    import com.github.nicoalvarezz.learningtestcontainers.service.CustomerService;
    import okhttp3.Call;
    import okhttp3.OkHttpClient;
    import okhttp3.Request;
    import okhttp3.Response;
    import org.junit.ClassRule;
    import org.junit.jupiter.api.AfterAll;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.testcontainers.containers.DockerComposeContainer;
    import org.testcontainers.containers.wait.strategy.Wait;

    import java.io.File;
    import java.io.IOException;
    import java.time.Duration;
    import java.util.List;
    import java.util.UUID;

    public class CustomerResourceTest {

    //    private static final GenericContainer<?> genericContainer = new GenericContainer<>(
    //            new ImageFromDockerfile()
    //                    .withFileFromPath("Dockerfile", Path.of("Dockerfile"))
    //    );

        @ClassRule
        public static DockerComposeContainer<?> dockerComposeContainer = new DockerComposeContainer<>(
                new File("docker-compose.yml")
        ).withExposedService("customer-service_1", 8080,
                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
        .withExposedService("db_1", 5432,
                             Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)));


        private CustomerService customerService;

        @BeforeEach
        void setUp() {
            dockerComposeContainer.start();
            DBConnectionProvider conn = DBConnectionProvider.getInstance();
            conn.setUrl("jdbc:postgresql://127.0.0.1:5432/testdb");
            conn.setUsername("test");
            conn.setPassword("test");
            customerService = new CustomerService();
        }

        @AfterAll
        static void afterAll() {
            dockerComposeContainer.stop();
        }

        @Test
        void registerCustomerEndpointTest() throws IOException {
            Request request = new Request.Builder()
                    .url("http://localhost:8080/api/v1/customer/register-customer?name=Nico")
                    .build();

            try (Response response = new OkHttpClient()
                    .newCall(request)
                    .execute()) {

                Assertions.assertEquals(201, response.code());

                // Ensure the response body is fully consumed.
                // We want to make sure the boy is fully read, so  we don't read the trailers too early.
                String responseBody = response.body().string();

                Assertions.assertEquals("value1", response.trailers().get("key1"));
            }


            List<Customer> customers = customerService.getAllCustomers();

            Assertions.assertEquals(1, customers.size());
            Assertions.assertEquals("Nico", customers.get(0).name());

        }

        @Test
        void getAllCustomersEndpointTest() throws IOException {
            customerService.createCustomer(new Customer(UUID.randomUUID().toString(), "Macarena"));

            Request request = new Request.Builder().url("http://localhost:8080/api/v1/customer/all-customers").build();

            Call call = new OkHttpClient().newCall(request);
            Response response = call.execute();

            Assertions.assertEquals(200, response.code());
        }
    }
