package org.acme.accountregistry.repository;

import java.util.Optional;

import org.acme.accountregistry.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.acme.accountregistry.fixtures.DataUtils.account;
import static org.acme.accountregistry.fixtures.DataUtils.bankAccount;
import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryTest extends BaseRepositoryTest {

    private Account defaultAccount;

    @Autowired
    private AccountRepository repository;

    @BeforeEach
    void setUp() {
        final Account accountHolder = account();
        bankAccount(accountHolder);
        defaultAccount = repository.save(accountHolder);
    }

    @Test
    void getPrincipalBy() {
        final Optional<UserDetails> result = repository.getPrincipalBy(defaultAccount.getPrincipal().getUsername());

        assertThat(result).isPresent();
    }
}