package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PostLoad;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.springframework.web.server.ResponseStatusException;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ToString
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Money {

    @NotNull(message = "The amount of money is mandatory")
    private BigDecimal amount;

    @Immutable
    @Column(updatable = false)
    @NotNull(message = "The currency of the money is mandatory")
    private Currency currency;

    public Money(final BigDecimal amount, final Currency currency) {
        this.amount = setDefaultScale(amount);
        this.currency = currency;
    }

    @PostLoad
    private void postLoadSetUp() {
        amount = setDefaultScale(amount);
    }

    private BigDecimal setDefaultScale(final BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    void add(final Money money) {
        validateSameCurrency(money);
        amount = amount.add(money.getAmount());
    }

    void subtract(final Money money) {
        validateSameCurrency(money);
        amount = amount.subtract(money.getAmount());
    }

    private void validateSameCurrency(final Money otherMoney) {
        if (!getCurrency().equals(otherMoney.getCurrency())) {
            throw new ResponseStatusException(
              BAD_REQUEST,
              "Its only supported to operate Money with same currency"
            );
        }
    }
}
