package org.acme.accountregistry.application.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.service.AccountAuthenticationEventService;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class AuthenticationEventHandler {

    private final AccountAuthenticationEventService service;

    @EventListener
    void onSuccess(final AuthenticationSuccessEvent loginSuccessEvent) {
        final Authentication authentication = loginSuccessEvent.getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.debug("Account successfully authenticated. And user set to the security context. username={}",
                  authentication.getName());
        service.createSuccessEvent(loginSuccessEvent);
    }

    @EventListener
    void onFailure(final AbstractAuthenticationFailureEvent loginFailureEvent) {
        service.createFailureEvent(loginFailureEvent);
    }
}
