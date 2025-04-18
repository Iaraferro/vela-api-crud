package com.iaramartins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
@NotBlank @Size(min=2, max=100) String nome,
@Email @NotBlank String email,
@NotBlank @Pattern(regexp = "^\\+?[0-9\\s-]+$")String telefone) {
} 


