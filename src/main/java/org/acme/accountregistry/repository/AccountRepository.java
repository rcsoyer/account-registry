package org.acme.accountregistry.repository;

import java.util.Optional;

import org.acme.accountregistry.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account.principal.username AS username, " +
             "account.principal.password AS password " +
             "FROM Account account " +
             "WHERE account.principal.username = ?1")
    Optional<UserDetails> getPrincipalBy(String username);
}
