package org.acme.accountregistry.domain.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.Valid;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_account_id", updatable = false)
    @NotNull(message = "The bank account is mandatory")
    private BankAccount bankAccount;

    @Valid
    @NotNull(message = "The transaction amount is mandatory")
    private Money amount;

    protected AccountTransaction(final TransactionType transactionType,
                                 final BankAccount bankAccount,
                                 final Money amount,
                                 final BigDecimal balanceBeforeTransaction) {
        this.transactionType = transactionType;
        this.bankAccount = bankAccount;
        this.amount = amount;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = bankAccount.getBalance().getAmount();
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT
    }
}
