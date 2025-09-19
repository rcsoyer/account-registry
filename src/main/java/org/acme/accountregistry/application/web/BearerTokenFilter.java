package org.acme.accountregistry.application.web;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.acme.accountregistry.domain.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class BearerTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@Nonnull final HttpServletRequest request,
                                    @Nonnull final HttpServletResponse response,
                                    @Nonnull final FilterChain filterChain)
      throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            setSecurityContext(authorizationHeader.substring(7));
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(final String jwt) {
        final Consumer<Jws<Claims>> setAuthentication = jwsClaims -> {
            final String username = jwsClaims.getPayload().getSubject();
            SecurityContextHolder
              .getContext()
              .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, List.of()));
        };

        jwtService.decodeJwt(jwt)
                  .ifPresentOrElse(setAuthentication, SecurityContextHolder::clearContext);
    }
}
