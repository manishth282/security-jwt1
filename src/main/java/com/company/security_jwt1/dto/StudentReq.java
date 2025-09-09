package com.company.security_jwt1.dto;

import jakarta.validation.constraints.*;

import java.util.Set;

public record StudentReq(
        @NotBlank String name,
        @NotBlank String rollNo,
        @Email(message = "Please enter valid email") String email,
        @Size(min = 4, message = "Password should minimum 4 character") String password,
        @NotEmpty Set<String> roles
        ) {}
