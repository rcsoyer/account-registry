package org.acme.accountregistry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Slf4j
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("inttest")
@Import(TestcontainersConfig.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = AccountRegistryApplication.class)
class AccountRegistryApplicationTests {

    @Test
    void contextLoads() {
        log.info("Simple test to verify the application runs as expectedly when deployed");
    }

}
