package org.acme.accountregistry.application.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class AuthenticationEventHandler {

    @EventListener
    void onSuccess(final AuthenticationSuccessEvent loginSuccessEvent) {
        final Authentication authentication = loginSuccessEvent.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Successfully authenticated user {}", authentication.getName());
    }

    @EventListener
    void onFailure(final AbstractAuthenticationFailureEvent loginFailureEvent) {
        log.warn("Failure authenticated user {}", loginFailureEvent.getAuthentication().getName());
    }
}
