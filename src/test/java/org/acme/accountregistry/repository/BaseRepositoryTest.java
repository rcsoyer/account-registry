package org.acme.accountregistry.repository;

import java.util.Optional;

import org.acme.accountregistry.BaseTestContainer;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@ActiveProfiles("inttest")
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(classes = {BaseRepositoryTest.JpaTestConfig.class})
abstract class BaseRepositoryTest extends BaseTestContainer {

    @TestConfiguration
    @EnableJpaAuditing
    static class JpaTestConfig {

        @Bean
        AuditorAware<String> auditorProvider() {
            return () -> Optional.of("jpa-test_user");
        }
    }
}
