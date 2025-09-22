package org.acme.accountregistry.domain.dto.query;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import org.iban4j.Iban;

public interface TransferAccountOverview extends Serializable {

    @NotNull
    Iban getAccountNumber();

    @NotNull
    String getAccountHolderName();
}
