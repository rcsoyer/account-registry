package org.acme.accountregistry.infrastructure.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.iban4j.Iban;
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

    @Bean
    Module ibanModule() {
        return new SimpleModule("ibanModule")
                 .addSerializer(Iban.class, new JsonSerializer<>() {
                     @Override
                     public void serialize(final Iban iban, final JsonGenerator jsonGenerator,
                                           final SerializerProvider serializers) throws IOException {
                         jsonGenerator.writeString(iban.toString());
                     }
                 });
    }
}