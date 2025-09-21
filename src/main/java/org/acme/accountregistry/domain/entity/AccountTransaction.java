package org.acme.accountregistry.domain.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * Base class for financial transactions.
 */
@Getter
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
abstract sealed class AccountTransaction extends BaseImmutableEntity
  permits AccountTransactionTransfer {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The transaction type is mandatory")
    private TransactionType transactionType;

    @NotNull(message = "The balance before the transaction is mandatory")
    private BigDecimal balanceBeforeTransaction;

    @NotNull(message = "The balance after the transaction is mandatory")
    private BigDecimal balanceAfterTransaction;

    protected AccountTransaction(final TransactionType transactionType,
                                 final BigDecimal balanceBeforeTransaction,
                                 final BigDecimal balanceAfterTransaction) {
        this.transactionType = transactionType;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT
    }
}
