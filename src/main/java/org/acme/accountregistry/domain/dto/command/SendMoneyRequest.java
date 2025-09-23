package org.acme.accountregistry.domain.dto.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.io.Serial;
import java.io.Serializable;
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
                               String recipientName)
  implements Serializable {

    @Serial
    private static final long serialVersionUID = 8743526851248970123L;
}
