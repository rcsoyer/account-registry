package org.acme.accountregistry.web;

import java.io.IOException;
import java.util.List;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.acme.accountregistry.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class BearerTokenFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            setSecurityContext(authorizationHeader.substring(7));
        }

        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(final String jwt) {
        final Claims claims = jwtService.decodeJwt(jwt).getPayload();
        final String username = claims.getSubject();
        final List<GrantedAuthority> authorities = List.of(claims.get("roles", GrantedAuthority[].class));
        SecurityContextHolder
          .getContext()
          .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
    }
}
