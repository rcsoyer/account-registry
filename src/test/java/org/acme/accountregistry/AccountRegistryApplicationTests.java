package org.acme.accountregistry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("inttest")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = AccountRegistryApplication.class)
class AccountRegistryApplicationTests extends BaseTestContainer {

    @Test
    void contextLoads() {
    }

}
