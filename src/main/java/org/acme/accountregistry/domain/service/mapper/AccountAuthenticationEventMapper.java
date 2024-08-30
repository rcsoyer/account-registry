package org.acme.accountregistry.domain.service.mapper;

import org.acme.accountregistry.domain.entity.Account;
import org.acme.accountregistry.domain.entity.AccountAuthenticationEvent;
import org.acme.accountregistry.domain.entity.AccountAuthenticationEvent.AuthenticationEventType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Mapper(
        uses = {AccountMapper.class},
        imports = {WebAuthenticationDetails.class, AuthenticationEventType.class})
public interface AccountAuthenticationEventMapper {

    @Mapping(
            target = "remoteAddress",
            expression =
                    "java(((WebAuthenticationDetails) event.getAuthentication().getDetails()).getRemoteAddress())")
    @Mapping(target = "authenticationTimestamp", expression = "java(event.getTimestamp())")
    @Mapping(target = "eventType", expression = "java(AuthenticationEventType.SUCCESS)")
    AccountAuthenticationEvent sucessAccountAuthenticationEvent(
            AuthenticationSuccessEvent event, Account account);
}
