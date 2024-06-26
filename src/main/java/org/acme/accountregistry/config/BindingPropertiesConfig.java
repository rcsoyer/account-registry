package org.acme.accountregistry.config;

import org.acme.accountregistry.binding.SecurityJwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SecurityJwtProperties.class)
class BindingPropertiesConfig {
}
