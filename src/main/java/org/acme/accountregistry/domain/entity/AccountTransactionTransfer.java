package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import static lombok.AccessLevel.PROTECTED;
import static org.acme.accountregistry.domain.entity.AccountTransaction.TransactionType.TRANSFER;
import static org.acme.accountregistry.domain.entity.AccountTransactionTransfer.TransferType.MONEY_OUT;

@Entity
@Getter
@Immutable
@NoArgsConstructor(access = PROTECTED)
public non-sealed class AccountTransactionTransfer extends AccountTransaction {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The transfer account is mandatory")
    private TransferType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_account_id", updatable = false)
    @NotNull(message = "The bank account is mandatory")
    private BankAccount bankAccount;

    @Valid
    @NotNull(message = "The other account in this transfer is mandatory")
    private TransferAccount transferAccount;

    @Valid
    @NotNull(message = "The transfer amount is mandatory")
    private Money amount;

    private AccountTransactionTransfer(final TransferType type,
                                       final BankAccount bankAccount,
                                       final TransferAccount transferAccount,
                                       final Money amount,
                                       final BigDecimal balanceBeforeTransaction) {
        super(TRANSFER, balanceBeforeTransaction, bankAccount.getBalance().getAmount());
        this.type = type;
        this.bankAccount = bankAccount;
        this.transferAccount = transferAccount;
        this.amount = amount;
    }

    public static AccountTransactionTransfer moneyOut(final BankAccount bankAccount,
                                                      final TransferAccount transferAccount,
                                                      final Money amount) {
        final BigDecimal balanceBefore = bankAccount.getBalance().getAmount();
        bankAccount.debitMoney(amount);
        return new AccountTransactionTransfer(MONEY_OUT, bankAccount, transferAccount, amount,
                                              balanceBefore);
    }

    /**
     * Money is transferred either in or out of this system's {@link BankAccount}.
     */
    public enum TransferType {
        MONEY_IN,
        MONEY_OUT
    }

}
