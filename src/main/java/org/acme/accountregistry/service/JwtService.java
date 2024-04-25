package org.acme.accountregistry.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final MacAlgorithm SIGN_ALG = Jwts.SIG.HS512;

    private final SecretKey secretKey;
    private final long jwtExpirationHours;

    public JwtService(@Value("${security.jwt.secret-key}") final String secretKey,
                      @Value("${security.jwt.expiration-hours}") final int expirationHours,
                      final Converter<String, SecretKey> secretKeyConverter) {
        this.secretKey = secretKeyConverter.convert(secretKey);
        this.jwtExpirationHours = expirationHours;
    }

    public String generateJwt(final Authentication authentication) {
        final var principal = (UserDetails) authentication.getPrincipal();
        final Instant jwtExpiration = LocalDateTime.now().plusHours(jwtExpirationHours).toInstant(ZoneOffset.UTC);
        return Jwts.builder()
                   .id(UUID.randomUUID().toString())
                   .subject((principal.getUsername()))
                   .issuedAt(new Date())
                   .expiration(Date.from(jwtExpiration))
                   .signWith(secretKey, SIGN_ALG)
                   .issuer("acme.org")
                   .claim("AUTHORITIES", principal.getAuthorities())
                   .compact();
    }
}
