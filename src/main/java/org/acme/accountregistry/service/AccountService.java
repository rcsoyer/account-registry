package org.acme.accountregistry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.Account;
import org.acme.accountregistry.repository.AccountRepository;
import org.acme.accountregistry.service.dto.AccountRegisterRequest;
import org.acme.accountregistry.service.dto.AccountRegisterResponse;
import org.acme.accountregistry.service.mapper.AccountMapper;
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
        final String password = passwordEncoder.encode(PasswordUtils.generateRandomPassword());
        final Account account = mapper.toEntity(request, password);
        return mapper.toResponse(repository.save(account));
    }

}
