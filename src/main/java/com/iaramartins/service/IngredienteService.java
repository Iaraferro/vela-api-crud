package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Ingrediente;

public interface IngredienteService {
    List<Ingrediente> listar();
    Ingrediente buscarPorId(Long id);
    Ingrediente salvar(Ingrediente ingrediente);
    Ingrediente atualizar(Long id, Ingrediente ingrediente);
    boolean deletar(Long id);
}
