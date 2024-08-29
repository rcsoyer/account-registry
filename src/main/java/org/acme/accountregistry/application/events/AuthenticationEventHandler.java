package org.acme.accountregistry.application.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class AuthenticationEventHandler {

    @EventListener
    void onSuccess(final AuthenticationSuccessEvent loginSuccessEvent) {
        final Authentication authentication = loginSuccessEvent.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug(
                "Account successfully authenticated. And user set to the security context. username={}",
                authentication.getName());
    }

    @EventListener
    void onFailure(final AbstractAuthenticationFailureEvent loginFailureEvent) {
        log.warn("Failure authenticated user {}", loginFailureEvent.getAuthentication().getName());
        final AuthenticationException failureError = loginFailureEvent.getException();

        switch (failureError) {
            case UsernameNotFoundException usernameNotFound ->
                    log.warn("Username not found", usernameNotFound);
            case BadCredentialsException badCredentials ->
                    log.warn("Bad credentials provided", badCredentials);
            default -> log.error("Unexpected Authentication error", failureError);
        }
    }

    @EventListener
    void onFailure(final AuthenticationFailureBadCredentialsEvent loginFailureEvent) {
        final AuthenticationException badCredentials = loginFailureEvent.getException();
        log.warn("Bad credentials provided", badCredentials);
    }
}
