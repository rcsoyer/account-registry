package org.acme.accountregistry.domain.dto.command;

import java.math.BigDecimal;
import java.util.Currency;
import org.iban4j.Iban;

public record SendMoneyRequest(BigDecimal amount, Currency currency,
                               Iban recipientIban, String recipientName,
                               long senderBankAccountId) {
}
