package org.acme.accountregistry.domain.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@MappedSuperclass
@NoArgsConstructor(access = PROTECTED)
abstract sealed class AccountTransaction extends BaseImmutableEntity
  permits AccountTransactionTransfer {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The transaction type is mandatory")
    private TransactionType transactionType;

    protected AccountTransaction(final TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT
    }
}
