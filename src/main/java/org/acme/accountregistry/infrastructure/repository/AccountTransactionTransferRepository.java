package org.acme.accountregistry.infrastructure.repository;

import org.acme.accountregistry.domain.entity.AccountTransactionTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionTransferRepository
  extends JpaRepository<AccountTransactionTransfer, Long> {
}
