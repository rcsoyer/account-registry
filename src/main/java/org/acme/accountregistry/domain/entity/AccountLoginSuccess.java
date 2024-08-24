package org.acme.accountregistry.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.time.Instant;

@Getter
// @Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountLoginSuccess extends AbstractImmutableEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    private Instant loginEventTimestamp;

    @Embedded private WebAuthenticationDetails authenticationDetails;

    @Builder
    private AccountLoginSuccess(
            final Account account,
            final long loginEventTimestamp,
            final WebAuthenticationDetails authenticationDetails) {
        this.account = account;
        this.loginEventTimestamp = Instant.ofEpochMilli(loginEventTimestamp);
        this.authenticationDetails = authenticationDetails;
    }
}
