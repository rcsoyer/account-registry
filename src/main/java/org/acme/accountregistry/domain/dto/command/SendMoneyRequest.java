package org.acme.accountregistry.domain.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Currency;
import org.iban4j.Iban;

public record SendMoneyRequest(@NotNull(message = "The amount is mandatory")
                               @Positive(message = "The amount must be positive")
                               BigDecimal amount,

                               @NotNull(message = "The currency is mandatory")
                               Currency currency,

                               @NotNull(message = "The recipient IBAN is mandatory")
                               Iban recipientIban,

                               @NotBlank(message = "The recipient name is mandatory")
                               String recipientName,

                               @Positive(message = "The sender bank account ID must be positive")
                               long senderBankAccountId) {
}
