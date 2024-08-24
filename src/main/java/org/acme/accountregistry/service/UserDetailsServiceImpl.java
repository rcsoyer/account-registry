package org.acme.accountregistry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.infrastructure.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom implementation of {@link UserDetailsService} to load user details from the database.
 * <br/> It's aimed to be used just by the Spring Security to authenticate users via
 * {@link org.springframework.security.authentication.AuthenticationProvider}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    //TODO store all the data from UserDetails into the database
    @Override
    public UserDetails loadUserByUsername(final String username) {
        return accountRepository
                 .getPrincipalBy(username)
                 .map(userDetails ->
                        User.withUsername(userDetails.getUsername())
                            .password(userDetails.getPassword())
                            .build()
                 )
                 .orElseThrow(() -> {
                     log.warn("User with username={} is not registered in the platform", username);
                     return new UsernameNotFoundException("User is not registered in the platform");
                 });
    }
}
