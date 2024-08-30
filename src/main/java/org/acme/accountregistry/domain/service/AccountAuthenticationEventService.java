package org.acme.accountregistry.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.acme.accountregistry.domain.service.mapper.AccountAuthenticationEventMapper;
import org.acme.accountregistry.infrastructure.repository.AccountAuthenticationEventRepository;
import org.acme.accountregistry.infrastructure.repository.AccountRepository;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountAuthenticationEventService {

    private final AccountAuthenticationEventRepository eventRepository;
    private final AccountRepository accountRepository;
    private final AccountAuthenticationEventMapper mapper;

    public void createSuccessEvent(final AuthenticationSuccessEvent event) {
        final String username = event.getAuthentication().getName();
        accountRepository
                .findAccountByPrincipalUsername(username)
                .map(account -> mapper.sucessAccountAuthenticationEvent(account, event))
                .map(eventRepository::save)
                .orElseThrow(errorAccountNotFound(username));
    }

    private Supplier<IllegalStateException> errorAccountNotFound(final String username) {
        return () -> {
            log.error(
                    "Unexpected error that shouldn't happen. "
                            + "The Spring's Security context published an authentication success event "
                            + "that the username doesn't exists in the system. username={}",
                    username);
            return new IllegalStateException("Account not found");
        };
    }
}
