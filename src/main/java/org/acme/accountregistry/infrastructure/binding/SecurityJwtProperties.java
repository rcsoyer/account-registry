package org.acme.accountregistry.infrastructure.binding;

import javax.crypto.SecretKey;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {

    @NotNull
    private final SecretKey secretKey;

    @Positive
    private final int expirationHours;

    @ConstructorBinding
    public SecurityJwtProperties(final SecretKey secretKey, final int expirationHours) {
        this.secretKey = secretKey;
        this.expirationHours = expirationHours;
    }
}
