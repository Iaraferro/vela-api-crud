package com.iaramartins.service;

import java.util.Set;

public interface JwtService {
    String generateToken(String login, Set<String> roles);
}
