package org.acme.accountregistry.domain.entity;

import static jakarta.persistence.EnumType.STRING;

import static lombok.AccessLevel.PROTECTED;

import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE_BAD_CREDENTIALS;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.SUCCESS;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

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

    private AccountAuthenticationEvent(
            final Account account,
            final AbstractAuthenticationEvent event,
            final AuthenticationEventType eventType) {
        this.account = account;
        this.authenticationTimestamp = Instant.ofEpochMilli(event.getTimestamp());
        this.eventType = eventType;
    }

    public AccountAuthenticationEvent(
            final Account account, final AuthenticationSuccessEvent event) {
        this(account, event, SUCCESS);
        setRemoteAddress(event);
    }

    public AccountAuthenticationEvent(
            final Account account, final AuthenticationFailureBadCredentialsEvent event) {
        this(account, event, FAILURE_BAD_CREDENTIALS);
    }

    private void setRemoteAddress(final AuthenticationSuccessEvent event) {
        try {
            final String remoteAddress =
                    ((WebAuthenticationDetails) event.getAuthentication().getDetails())
                            .getRemoteAddress();
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
