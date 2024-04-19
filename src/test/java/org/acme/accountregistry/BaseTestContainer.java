package org.acme.accountregistry;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class BaseTestContainer {

    private static final MySQL8Container MYSQL_CONTAINER;

    static {
        MYSQL_CONTAINER = new MySQL8Container()
                            .withUsername("root")
                            .withPassword("root")
                            .withReuse(true);
        MYSQL_CONTAINER.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    }

    private static class MySQL8Container extends MySQLContainer<MySQL8Container> {
        private MySQL8Container() {
            super("mysql:8.0");
        }
    }
}