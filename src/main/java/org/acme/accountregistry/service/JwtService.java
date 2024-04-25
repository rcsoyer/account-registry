package org.acme.accountregistry.service;

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

    public static final MacAlgorithm SIGN_ALG = Jwts.SIG.HS512;

    private final SecretKey secretKey;

    public JwtService(@Value("${security.jwt.secret-key}") final String secretKey,
                      final Converter<String, SecretKey> secretKeyConverter) {
        this.secretKey = secretKeyConverter.convert(secretKey);
    }

    public String generateJwt(final Authentication authentication) {
        final var principal = (UserDetails) authentication.getPrincipal();
        final long jwtExpirationMs = 86400000;
        return Jwts.builder()
                   .id(UUID.randomUUID().toString())
                   .subject((principal.getUsername()))
                   .issuedAt(new Date())
                   .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                   .signWith(secretKey, SIGN_ALG)
                   .issuer("acme.org")
                   .claim("AUTHORITIES", principal.getAuthorities())
                   .compact();
    }
}
