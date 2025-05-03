package com.iaramartins.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
@NotBlank(message = "Nome é obrigatório") 
@Size(min=3, max=100, message = "Nome deve ter entre 3 e 100 caracteres" ) 
String nome,

@NotBlank(message = "Email é obrigatório")
@Email(message = "Email deve ser válido")
String email,

@NotBlank(message = "Telefone é obrigatório")
@Pattern(regexp = "^\\+?[0-9\\s-]+$", message = "Telefone deve ter 10 ou 11 dígitos")
String telefone,

@NotBlank(message = "Senha é obrigatória")
@Size(min = 6, max = 100, message = "Senha deve ter entre 6 e 100 caracteres")
String senha,

@NotBlank(message = "Role é obrigatória")
String role)

{
} 


