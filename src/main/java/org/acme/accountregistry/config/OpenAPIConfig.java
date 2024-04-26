package org.acme.accountregistry.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI config
 */
@Configuration
class OpenAPIConfig {

    @Bean
    OpenAPI openAPI(final BuildProperties buildProperties) {
        final var schemeName = "bearerAuth";
        final var bearerFormat = "JWT";
        final var scheme = "bearer";
        final var securityScheme = new SecurityScheme()
                                     .name(schemeName)
                                     .type(SecurityScheme.Type.HTTP)
                                     .bearerFormat(bearerFormat)
                                     .in(SecurityScheme.In.HEADER)
                                     .scheme(scheme);
        final var components = new Components()
                                 .addSecuritySchemes(schemeName, securityScheme);
        final var info = new Info()
                           .title("Account-Registry Service - API")
                           .description("A web app microservice application")
                           .version(buildProperties.getVersion());
        return new OpenAPI()
                 .info(info)
                 .addSecurityItem(new SecurityRequirement().addList(schemeName))
                 .components(components);
    }
}
