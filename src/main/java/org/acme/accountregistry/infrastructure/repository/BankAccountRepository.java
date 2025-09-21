package org.acme.accountregistry.infrastructure.repository;

import java.util.List;

import org.acme.accountregistry.domain.entity.BankAccount;
import org.acme.accountregistry.domain.dto.query.AccountOverview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    @Query("SELECT bankAccount.iban AS accountNumber, " +
             "bankAccount.type AS accountType, " +
             "bankAccount.balance.amount AS balance, " +
             "bankAccount.balance.currency AS currency " +
             "FROM BankAccount bankAccount " +
             "JOIN Account accountHolder ON bankAccount.accountHolder = accountHolder " +
             "WHERE accountHolder.user.username = ?1")
    List<AccountOverview> findByUsername(String username);
}
