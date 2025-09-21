package org.acme.accountregistry.domain.dto.command;

import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;

public record AccountRegisterResponse(@NotNull String username, @NotNull String password)
  implements Serializable {

    @Serial
    private static final long serialVersionUID = -8542763547862354876L;
}
