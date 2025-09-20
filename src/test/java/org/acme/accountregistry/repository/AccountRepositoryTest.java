package org.acme.accountregistry.repository;

import org.acme.accountregistry.domain.entity.Account;
import org.acme.accountregistry.infrastructure.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.acme.accountregistry.fixtures.DataUtils.account;
import static org.acme.accountregistry.fixtures.DataUtils.bankAccount;

class AccountRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private AccountRepository repository;

    @Test
    void save() {
        final Account accountHolder = account();
        bankAccount(accountHolder);
        repository.save(accountHolder);
    }
}