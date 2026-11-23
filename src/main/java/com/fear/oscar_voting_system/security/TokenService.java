package com.fear.oscar_voting_system.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class TokenService {
    private final byte[] secret;

    public TokenService(@Value("${oscar.auth.secret:local-development-secret-change-before-deploy-32chars}") String secret) {
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32)
            throw new IllegalArgumentException("oscar.auth.secret precisa ter pelo menos 32 bytes");
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
    }

    public String issue(UUID userId, String role) {
        String payload = userId + "." + role + "." + Instant.now().plusSeconds(86400).getEpochSecond();
        String encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        return encoded + "." + sign(encoded);
    }

    public Claims verify(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 2 || !constantTimeEquals(sign(parts[0]), parts[1])) return null;
        try {
            String[] claims = new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8).split("\\.");
            long expiry = Long.parseLong(claims[2]);
            if (claims.length != 3 || expiry <= Instant.now().getEpochSecond()) return null;
            return new Claims(UUID.fromString(claims[0]), claims[1]);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private boolean constantTimeEquals(String left, String right) {
        return java.security.MessageDigest.isEqual(left.getBytes(StandardCharsets.UTF_8), right.getBytes(StandardCharsets.UTF_8));
    }

    public record Claims(UUID userId, String role) {}
}
