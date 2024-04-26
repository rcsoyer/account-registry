package org.acme.accountregistry.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.domain.Account;
import org.acme.accountregistry.domain.BankAccount;
import org.acme.accountregistry.repository.AccountRepository;
import org.acme.accountregistry.repository.BankAccountRepository;
import org.acme.accountregistry.repository.projection.AccountOverview;
import org.acme.accountregistry.service.dto.AccountRegisterRequest;
import org.acme.accountregistry.service.dto.AccountRegisterResponse;
import org.acme.accountregistry.service.mapper.AccountMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.acme.accountregistry.service.PasswordUtils.generateRandomPassword;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountRepository bankAccountRepository;

    @Transactional(readOnly = true)
    public List<AccountOverview> getAccountOverview(final Authentication authentication) {
        log.debug("Retrieving the Account Overview");
        return bankAccountRepository
                 .findByUsername(authentication.getName());
    }

    public AccountRegisterResponse openAccount(final AccountRegisterRequest request) {
        log.debug("Registering a new User's Account with default opening Payments Bank Account");
        checkUsernameAvailability(request.username());
        final String rawPassword = generateRandomPassword();
        final String encodedPassword = passwordEncoder.encode(rawPassword);
        final Account account = mapper.toEntity(request, encodedPassword);
        new BankAccount(account, BankAccount.Type.PAYMENTS);
        setSecurityContext(account);
        accountRepository.save(account);
        return mapper.toResponse(account, rawPassword);
    }

    private void checkUsernameAvailability(final String username) {
        log.debug("Check if the username is available");
        if (accountRepository.existsAccountByPrincipalUsername(username)) {
            throw new ResponseStatusException(CONFLICT, "The username is already in use");
        }
    }

    private void setSecurityContext(final Account account) {
        log.debug("Setting the Security Context for the new Account");
        final String username = account.getPrincipal().getUsername();
        final String password = account.getPrincipal().getPassword();
        SecurityContextHolder
          .getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
    }

}
