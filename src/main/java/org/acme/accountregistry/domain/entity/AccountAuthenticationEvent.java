package org.acme.accountregistry.domain.entity;

import static jakarta.persistence.EnumType.STRING;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountAuthenticationEvent extends AbstractImmutableEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @NotNull @PastOrPresent private Instant authenticationTimestamp;

    private InetAddress remoteAddress;

    @NotNull
    @Enumerated(STRING)
    private AuthenticationEventType eventType;

    @Builder
    private AccountAuthenticationEvent(
            final Account account,
            final long authenticationTimestamp,
            final String remoteAddress,
            final AuthenticationEventType eventType) {
        this.account = account;
        this.authenticationTimestamp = Instant.ofEpochMilli(authenticationTimestamp);
        setRemoteAddress(remoteAddress);
        this.eventType = eventType;
    }

    private void setRemoteAddress(final String remoteAddress) {
        try {
            this.remoteAddress = InetAddress.getByAddress(remoteAddress.getBytes());
        } catch (final UnknownHostException notIpAddressError) {
            throw new IllegalArgumentException(
                    "Invalid IP Address propagated by Spring Security Authentication Event",
                    notIpAddressError);
        }
    }

    public enum AuthenticationEventType {
        SUCCESS,
        FAILURE,
        FAILURE_BAD_CREDENTIALS,
        FAILURE_USER_NOT_FOUND
    }
}
