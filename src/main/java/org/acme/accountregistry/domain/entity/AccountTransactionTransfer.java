package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Immutable
@NoArgsConstructor(access = PROTECTED)
public class AccountTransactionTransfer extends AccountTransaction {

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "The transfer account is mandatory")
    private TransferType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_account_id", updatable = false)
    @NotNull(message = "The bank account is mandatory")
    private BankAccount bankAccount;

    @Valid
    @NotNull(message = "The transfer account is mandatory")
    private TransferAccount transferAccount;

    public AccountTransactionTransfer(final TransferType type,
                                      final BankAccount bankAccount,
                                      final TransferAccount transferAccount) {
        super(TransactionType.TRANSFER);
        this.type = type;
        this.bankAccount = bankAccount;
        this.transferAccount = transferAccount;
    }

    /**
     * Money is transferred either in or out of this system's {@link BankAccount}.
     */
    public enum TransferType {
        MONEY_IN,
        MONEY_OUT
    }

}
