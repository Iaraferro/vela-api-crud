package com.iaramartins.Resource;

import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.Set;

public class TokenUtils {
     public static String generateToken(String username, Set<String> roles) {
        return Jwt.issuer("crud-api-vela")
            .upn(username)
            .groups(roles)
            .expiresIn(Duration.ofHours(1))
            .sign();
    }
    
    public static String generateAdminToken() {
        return generateToken("admin@test.com", Set.of("ADMIN"));
    }
    
    public static String generateUserToken() {
        return generateToken("user@test.com", Set.of("USER"));
    }

    public static String generateClientToken() {
    return generateToken("client@test.com", Set.of("CLIENTE"));
}
}
