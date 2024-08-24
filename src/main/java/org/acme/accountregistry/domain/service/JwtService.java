package org.acme.accountregistry.domain.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.acme.accountregistry.infrastructure.binding.SecurityJwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final int jwtExpirationHours;
    private final JwtParser jwtParser;

    public JwtService(final SecurityJwtProperties securityJwtProperties) {
        this.secretKey = securityJwtProperties.getSecretKey();
        this.jwtExpirationHours = securityJwtProperties.getExpirationHours();
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
    }

    public String generateJwt(final Authentication authentication) {
        final Instant jwtExpiration = LocalDateTime.now().plusHours(jwtExpirationHours).toInstant(ZoneOffset.UTC);
        return Jwts.builder()
                   .id(UUID.randomUUID().toString())
                   .subject((authentication.getName()))
                   .issuedAt(new Date())
                   .expiration(Date.from(jwtExpiration))
                   .signWith(secretKey, Jwts.SIG.HS512)
                   .issuer("acme.org")
                   .compact();
    }

    public Optional<Jws<Claims>> decodeJwt(final String jwt) {
        try {
            return Optional.of(jwtParser.parseSignedClaims(jwt));
        } catch (final Exception jwtError) {
            log.error("Invalid JWT was passed to the application", jwtError);
            return Optional.empty();
        }
    }
}
