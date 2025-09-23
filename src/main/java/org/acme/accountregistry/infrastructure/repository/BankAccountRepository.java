package org.acme.accountregistry.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import org.acme.accountregistry.domain.dto.query.AccountOverview;
import org.acme.accountregistry.domain.entity.BankAccount;
import org.iban4j.Iban;
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

    /**
     * Find a bank account by its id and verify the username for secure access.
     */
    @Query("SELECT bankAccount FROM BankAccount bankAccount "
             + "JOIN Account accountHolder ON bankAccount.accountHolder = accountHolder "
             + "WHERE bankAccount.id = ?1 AND accountHolder.user.username = ?2")
    Optional<BankAccount> findBy(long id, String username);

    Optional<BankAccount> findByIban(Iban iban);

    @Query("SELECT bankAccount "
             + "FROM BankAccount bankAccount "
             + "JOIN FETCH Account accountHolder ON bankAccount.accountHolder = accountHolder "
             + "JOIN FETCH User user ON accountHolder.user = user "
             + "WHERE bankAccount.iban = ?1")
    Optional<BankAccount> fetchByIban(Iban iban);
}
