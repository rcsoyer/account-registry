package org.acme.accountregistry.config;

import lombok.RequiredArgsConstructor;
import org.acme.accountregistry.application.web.BearerTokenFilter;
import org.acme.accountregistry.application.web.LoginSuccessHandler;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

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
                                    "/info",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/swagger-config",
                                    "/v3/api-docs*",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/webjars/**")
                   .requestMatchers(POST, "/accounts");
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity securityBuilder,
                                                   final BearerTokenFilter bearerTokenFilter) throws Exception {
        return securityBuilder
                 .csrf(AbstractHttpConfigurer::disable)
                 .sessionManagement(customizer -> customizer.sessionCreationPolicy(STATELESS))
                 .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                 .formLogin(customizeLogin())
                 .exceptionHandling(customizer -> customizer.accessDeniedHandler(problemSupport))
                 .addFilterBefore(bearerTokenFilter, UsernamePasswordAuthenticationFilter.class)
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
