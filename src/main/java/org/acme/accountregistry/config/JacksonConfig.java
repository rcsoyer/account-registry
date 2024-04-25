package org.acme.accountregistry.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
class JacksonConfig {

    @Bean
    Module problemModule() {
        return new ProblemModule();
    }

    @Bean
    Module constraintViolationProblemModule() {
        return new ConstraintViolationProblemModule();
    }
}
