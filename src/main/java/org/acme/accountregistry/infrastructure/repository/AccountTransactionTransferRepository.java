package org.acme.accountregistry.infrastructure.repository;

import org.acme.accountregistry.domain.dto.query.AccountTransactionOverview;
import org.acme.accountregistry.domain.entity.AccountTransactionTransfer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionTransferRepository
  extends JpaRepository<AccountTransactionTransfer, Long> {

    @Query("SELECT transactions.transactionType transactionType, "
             + "transactions.type transferType, "
             + "transactions.balanceBeforeTransaction balanceBeforeTransaction, "
             + "transactions.balanceAfterTransaction balanceAfterTransaction, "
             + "transactions.createdBy createdByUser, "
             + "transactions.createdAt createdAt, "
             + "transactions.bankAccount.iban userBankAccount, "
             + "transactions.transferAccount transferAccount, "
             + "transactions.amount.amount amount, "
             + "transactions.amount.currency currency "
             + "FROM AccountTransactionTransfer transactions "
             + "JOIN BankAccount bankAccount ON transactions.bankAccount = bankAccount "
             + "JOIN Account accountHolder ON bankAccount.accountHolder = accountHolder "
             + "WHERE transactions.bankAccount.id = ?1 "
             + "AND accountHolder.user.username = ?2")
    Slice<AccountTransactionOverview> findTransactions(long bankAccountId,
                                                       String username,
                                                       Pageable pageable);
}
