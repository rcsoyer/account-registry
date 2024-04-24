package org.acme.accountregistry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.Account;
import org.acme.accountregistry.domain.BankAccount;
import org.acme.accountregistry.repository.AccountRepository;
import org.acme.accountregistry.service.dto.AccountRegisterRequest;
import org.acme.accountregistry.service.dto.AccountRegisterResponse;
import org.acme.accountregistry.service.mapper.AccountMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AccountRegisterResponse createAccount(final AccountRegisterRequest request) {
        log.debug("Registering a new User's Account with default opening Payments Bank Account");
        final String rawPassword = PasswordUtils.generateRandomPassword();
        final String encodedPassword = passwordEncoder.encode(rawPassword);
        final Account account = mapper.toEntity(request, encodedPassword);
        new BankAccount(account, BankAccount.Type.PAYMENTS);
        setSecurityContext(account);
        repository.save(account);
        return mapper.toResponse(account, rawPassword);
    }

    private void setSecurityContext(final Account account) {
        final String username = account.getPrincipal().getUsername();
        final String password = account.getPrincipal().getPassword();
        SecurityContextHolder
          .getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
    }

}
