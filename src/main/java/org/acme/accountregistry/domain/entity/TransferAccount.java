package org.acme.accountregistry.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.iban4j.Iban;

import static lombok.AccessLevel.PROTECTED;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;

/**
 * Bank account details of an account used on a transfer.
 * <br/> This can be an account from this system or from an external bank
 */
@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class TransferAccount {

    @Column(updatable = false)
    @NotNull(message = "The account IBAN is mandatory")
    private Iban accountNumber;

    @Column(updatable = false)
    @NotBlank(message = "The account holder name is mandatory")
    @Size(max = 50, message = "The account holder name must be 50 characters or less")
    private String accountHolderName;

    public TransferAccount(final Iban accountNumber, final String accountHolderName) {
        this.accountNumber = accountNumber;
        this.accountHolderName = normalizeSpace(accountHolderName);
    }
}
