package org.acme.accountregistry.domain.service;

import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.entity.AccountAuthenticationEvent;
import org.acme.accountregistry.infrastructure.repository.AccountAuthenticationEventRepository;
import org.acme.accountregistry.infrastructure.repository.AccountRepository;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountAuthenticationEventService {

    private final AccountAuthenticationEventRepository eventRepository;
    private final AccountRepository accountRepository;

    public void createSuccessEvent(final AuthenticationSuccessEvent event) {
        log.debug("Persisting Account Authentication success event published by spring's security context. "
                    + "AuthenticationSuccessEvent={}", event);
        final String username = event.getAuthentication().getName();
        accountRepository
          .findAccountByPrincipalUsername(username)
          .map(account -> new AccountAuthenticationEvent(account, event))
          .map(eventRepository::save)
          .orElseThrow(errorAccountNotFound(username));
    }

    public void createFailureEvent(final AbstractAuthenticationFailureEvent event) {
        log.warn("Persisting Account Authentication failure event published by spring's security context. "
                   + "AbstractAuthenticationFailureEvent={}", event);
        final String username = event.getAuthentication().getName();
        accountRepository
          .findAccountByPrincipalUsername(username)
          .ifPresentOrElse(
            account -> eventRepository.save(new AccountAuthenticationEvent(account, event)),
            () -> eventRepository.save(new AccountAuthenticationEvent(event)));
    }

    private Supplier<IllegalStateException> errorAccountNotFound(final String username) {
        return () -> {
            log.error("Unexpected error that shouldn't happen. "
                        + "The Spring's Security context published an authentication success event "
                        + "that the username doesn't exists in the system. username={}",
                      username);
            return new IllegalStateException("Account not found");
        };
    }
}
