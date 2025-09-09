package com.company.security_jwt1.dto;

import java.util.Set;

public record StudentResponse(
        String name,
        String rollNo,
        String email,
        String password,
        Set<String> roles
) {}
