package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Telefone;

public interface TelefoneService {
    List<Telefone> listar();
    Telefone buscarPorId(Long id);
    Telefone salvar(Telefone telefone);
    Telefone atualizar(Long id, Telefone telefone);
    boolean deletar(Long id);
}
