package org.acme.accountregistry.config;

import org.acme.accountregistry.web.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity securityBuilder,
                                                   final LoginSuccessHandler loginSuccessHandler) throws Exception {
        return securityBuilder
                 .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                 .formLogin(customizer -> customizer.successHandler(loginSuccessHandler))
                 .build();
    }

    @Bean
    WebSecurityCustomizer httpRequestsPermitAll() {
        return customizer ->
                 customizer
                   .ignoring()
                   .requestMatchers(GET,
                                    "/health",
                                    "/health/liveness",
                                    "/health/readiness",
                                    "/info")
                   .requestMatchers(POST, "/accounts");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
