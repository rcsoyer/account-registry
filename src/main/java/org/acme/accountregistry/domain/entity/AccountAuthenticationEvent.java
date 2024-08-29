package org.acme.accountregistry.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountAuthenticationEvent extends AbstractImmutableEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @NotNull @PastOrPresent private Instant authenticationTimestamp;

    private String remoteAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthenticationEventType eventType;

    @Builder
    private AccountAuthenticationEvent(
            final Account account,
            final long loginEventTimestamp,
            final WebAuthenticationDetails authenticationDetails,
            final AuthenticationEventType eventType) {
        this.account = account;
        this.authenticationTimestamp = Instant.ofEpochMilli(loginEventTimestamp);
        this.remoteAddress = authenticationDetails.getRemoteAddress();
        this.eventType = eventType;
    }

    enum AuthenticationEventType {
        SUCCESS,
        FAILURE,
        FAILURE_BAD_CREDENTIALS,
        FAILURE_USER_NOT_FOUND
    }
}
