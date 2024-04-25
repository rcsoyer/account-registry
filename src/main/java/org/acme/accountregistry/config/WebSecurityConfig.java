package org.acme.accountregistry.config;

import lombok.RequiredArgsConstructor;
import org.acme.accountregistry.web.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(SecurityProblemSupport.class)
class WebSecurityConfig {

    private final SecurityProblemSupport problemSupport;
    private final LoginSuccessHandler loginSuccessHandler;

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
    SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity securityBuilder) throws Exception {
        return securityBuilder
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                 .formLogin(customizeLogin())
                 .exceptionHandling(customizer -> customizer.accessDeniedHandler(problemSupport))
                 .build();
    }

    private Customizer<FormLoginConfigurer<HttpSecurity>> customizeLogin() {
        return customizer -> customizer
                               .failureHandler(problemSupport)
                               .successHandler(loginSuccessHandler)
                               .permitAll();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
