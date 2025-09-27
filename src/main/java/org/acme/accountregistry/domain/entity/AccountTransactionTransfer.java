package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import static lombok.AccessLevel.PROTECTED;
import static org.acme.accountregistry.domain.entity.AccountTransaction.TransactionType.TRANSFER;
import static org.acme.accountregistry.domain.entity.AccountTransactionTransfer.TransferType.MONEY_IN;
import static org.acme.accountregistry.domain.entity.AccountTransactionTransfer.TransferType.MONEY_OUT;

@Entity
@Getter
@Immutable
@NoArgsConstructor(access = PROTECTED)
public non-sealed class AccountTransactionTransfer extends AccountTransaction {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "The transfer account is mandatory")
    private TransferType type;

    @Valid
    @NotNull(message = "The other account in this transfer is mandatory")
    private TransferAccount transferAccount;

    private AccountTransactionTransfer(final TransferType type,
                                       final BankAccount bankAccount,
                                       final TransferAccount transferAccount,
                                       final Money amount,
                                       final BigDecimal balanceBeforeTransaction) {
        super(TRANSFER, bankAccount, amount, balanceBeforeTransaction);
        this.type = type;
        this.transferAccount = transferAccount;
    }

    public static AccountTransactionTransfer moneyOut(final BankAccount bankAccount,
                                                      final TransferAccount transferAccount,
                                                      final Money amount) {
        final BigDecimal balanceBefore = bankAccount.getBalance().getAmount();
        bankAccount.debitMoney(amount);
        return new AccountTransactionTransfer(MONEY_OUT, bankAccount, transferAccount, amount,
                                              balanceBefore);
    }

    public static AccountTransactionTransfer moneyIn(final BankAccount bankAccount,
                                                     final TransferAccount transferAccount,
                                                     final Money amount) {
        final BigDecimal balanceBefore = bankAccount.getBalance().getAmount();
        bankAccount.topUp(amount);
        return new AccountTransactionTransfer(MONEY_IN, bankAccount, transferAccount, amount,
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
