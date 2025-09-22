package org.acme.accountregistry.domain.dto.query;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import org.iban4j.Iban;

/**
 * Data details related to a single BankAccount Transaction
 */
public interface AccountTransactionOverview extends Serializable {

    @NotNull
    String getTransactionType();

    @NotNull
    String getTransferType();

    @NotNull
    BigDecimal getBalanceBeforeTransaction();

    @NotNull
    BigDecimal getBalanceAfterTransaction();

    @NotNull
    String getCreatedByUser();

    @NotNull
    LocalDateTime getCreatedAt();

    @NotNull
    Iban getUserBankAccount();

    @NotNull
    TransferAccountOverview getTransferAccount();

    @NotNull
    BigDecimal getAmount();

    @NotNull
    Currency getCurrency();
}
