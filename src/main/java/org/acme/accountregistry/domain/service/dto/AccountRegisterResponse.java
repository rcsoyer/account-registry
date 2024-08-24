package org.acme.accountregistry.domain.service.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRegisterResponse(@NotNull String username, @NotNull String password) {
}
