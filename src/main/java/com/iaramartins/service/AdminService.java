package com.iaramartins.service;

import java.util.List;

import com.iaramartins.dto.AdminRequestDTO;
import com.iaramartins.dto.AdminResponseDTO;
import com.iaramartins.model.Admin;
import com.iaramartins.model.Cliente;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;


@ApplicationScoped
public class AdminService {

    @Inject
    EntityManager em;

    // Cadastra um novo admin
    @Transactional
    public AdminResponseDTO criarAdmin(AdminRequestDTO dto) {
        Admin admin = new Admin();
        admin.setEmail(dto.email());
        admin.setSenha(dto.senha()); // Na prática, use PasswordEncoder
        admin.setDepartamento(dto.departamento());
        admin.setRole("ADMIN");
        em.persist(admin); // Salva no banco
        
        return new AdminResponseDTO(
            admin.getId(),
            admin.getEmail(),
            admin.getDepartamento(),
            admin.getRole()
        );
    }

    // Busca admin por ID
    public AdminResponseDTO buscarPorId(Long id) {
        Admin admin = em.find(Admin.class,id);
        if (admin == null) {
            throw new NotFoundException("Admin não encontrado");
        }
        return converterParaDTO(admin);
    }

    // Lista todos os admins
    public List<AdminResponseDTO> listarTodos() {
        List<Admin> admins = em.createQuery("SELECT a FROM Admin a", Admin.class)
                              .getResultList();
        return admins.stream()
                   .map(admin -> converterParaDTO((Admin) admin))
                   .toList();
    }

    // Atualiza departamento
    @Transactional
    public void atualizarDepartamento(Long id, String novoDepartamento) {
        Admin admin = em.find(Admin.class, id);
        if (admin != null) {
            admin.setDepartamento(novoDepartamento);
        }
    }

    // Método auxiliar para converter Admin -> DTO
    private AdminResponseDTO converterParaDTO(Admin admin) {
        return new AdminResponseDTO(
            admin.getId(),
            admin.getEmail(),
            admin.getDepartamento(),
            admin.getRole()
        );
    }

    @Transactional
    public void banirCliente(Long clienteId) {
    // Busca o cliente (como entidade, não como DTO)
    Cliente cliente = em.find(Cliente.class, clienteId);
    if (cliente == null) {
        throw new NotFoundException("Cliente não encontrado");
    }
    cliente.setAtivo(false);
    }

    
}