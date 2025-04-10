package com.iaramartins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClienteRequestDTO(
@NotBlank String nome,
@Email @NotBlank String email,
@NotBlank String telefone) {
} 


