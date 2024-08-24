package org.acme.accountregistry.application.events;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class AuthenticationEventHandler {

    @EventListener
    void onSuccess(final AuthenticationSuccessEvent loginSuccessEvent) {
        log.debug(
                "Successfully authenticated user {}",
                loginSuccessEvent.getAuthentication().getName());
    }

    @EventListener
    void onFailure(final AbstractAuthenticationFailureEvent loginFailureEvent) {
        log.warn("Failure authenticated user {}", loginFailureEvent.getAuthentication().getName());
    }
}
