package org.acme.accountregistry.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Configuration for auditing JPA entities
 */
@Configuration
@EnableJpaAuditing
class AuditConfig {

    /**
     * The user that is logged in the app when creating or modifying resources
     */
    @Bean
    AuditorAware<String> auditorProvider() {
        return () -> Optional
                       .ofNullable(SecurityContextHolder.getContext().getAuthentication())
                       .map(Authentication::getName);
    }

}
