package org.acme.accountregistry.infrastructure.repository;

import java.util.Optional;
import org.acme.accountregistry.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsAccountByUserUsername(String username);

    Optional<Account> findAccountByUserUsername(String username);

}
