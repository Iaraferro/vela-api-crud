package com.iaramartins.service;

import java.time.Duration;
import java.util.Set;
import io.smallrye.jwt.build.Jwt;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtServiceImpl implements JwtService{
    
    @Override
    public String generateToken(String login, Set<String> roles){
        return Jwt.issuer("crud-api-vela")
                .upn(login)
                .groups(roles)
                .expiresIn(Duration.ofHours(24))
                .sign();
    }
}
