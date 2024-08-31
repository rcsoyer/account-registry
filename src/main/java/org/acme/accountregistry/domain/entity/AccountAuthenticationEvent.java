package org.acme.accountregistry.domain.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE_BAD_CREDENTIALS;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE_CREDENTIALS_EXPIRED;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE_LOCKED_ACCOUNT;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.FAILURE_USER_NOT_FOUND;
import static org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType.SUCCESS;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationFailureCredentialsExpiredEvent;
import org.springframework.security.authentication.event.AuthenticationFailureLockedEvent;
import org.springframework.security.authentication.event.AuthenticationFailureProviderNotFoundEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class AccountAuthenticationEvent extends AbstractImmutableEntity {

    @ManyToOne
    @JoinColumn(name = "account_id", updatable = false)
    private Account account;

    @NotNull
    @PastOrPresent
    private Instant authenticationTimestamp;

    private InetAddress remoteAddress;

    @NotNull
    @Enumerated(STRING)
    private AuthenticationEventType eventType;

    private AccountAuthenticationEvent(final Account account,
                                       final AbstractAuthenticationEvent event) {
        this.account = account;
        this.authenticationTimestamp = Instant.ofEpochMilli(event.getTimestamp());
    }

    private AccountAuthenticationEvent(final Account account,
                                       final AbstractAuthenticationEvent event,
                                       final AuthenticationEventType eventType) {
        this(account, event);
        this.eventType = eventType;
    }

    public AccountAuthenticationEvent(final Account account, final AuthenticationSuccessEvent event) {
        this(account, event, SUCCESS);
        setRemoteAddress(event);
    }

    public AccountAuthenticationEvent(final Account account,
                                      final AbstractAuthenticationFailureEvent event) {
        this(account, (AbstractAuthenticationEvent) event);

        switch (event) {
            case AuthenticationFailureBadCredentialsEvent ignored -> eventType = FAILURE_BAD_CREDENTIALS;
            case AuthenticationFailureProviderNotFoundEvent ignored -> eventType = FAILURE_USER_NOT_FOUND;
            case AuthenticationFailureCredentialsExpiredEvent ignored -> eventType = FAILURE_CREDENTIALS_EXPIRED;
            case AuthenticationFailureLockedEvent ignored -> eventType = FAILURE_LOCKED_ACCOUNT;
            default -> eventType = FAILURE;
        }
    }

    private void setRemoteAddress(final AuthenticationSuccessEvent event) {
        try {
            final String remoteAddress =
              ((WebAuthenticationDetails) event.getAuthentication().getDetails())
                .getRemoteAddress();
            this.remoteAddress = InetAddress.getByName(remoteAddress);
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
        FAILURE_USER_NOT_FOUND,
        FAILURE_CREDENTIALS_EXPIRED,
        FAILURE_LOCKED_ACCOUNT
    }
}
