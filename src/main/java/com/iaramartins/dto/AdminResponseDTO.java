package com.iaramartins.dto;

import com.iaramartins.model.Admin;

public record AdminResponseDTO(
    Long id,
    String email,
    String departamento,
    String role  // "ADMIN"
) {
    public static AdminResponseDTO fromEntity(Admin admin) {
        return new AdminResponseDTO(
            admin.getId(),
            admin.getEmail(),
            admin.getDepartamento(),
            admin.getRole()
        );
    }
}
