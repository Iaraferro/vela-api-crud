package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Ingrediente;

import jakarta.transaction.Transactional;

public class IngredienteServiceImpl implements IngredienteService{
    @Override
    public List<Ingrediente> listar() {
        return Ingrediente.listAll();
    }

    @Override
    public Ingrediente buscarPorId(Long id) {
        return Ingrediente.findById(id);
    }

    @Override
    @Transactional
    public Ingrediente salvar(Ingrediente ingrediente) {
        ingrediente.persist();
        return ingrediente;
    }

    @Override
    @Transactional
    public Ingrediente atualizar(Long id, Ingrediente ingredienteAtualizado) {
        Ingrediente ingrediente = Ingrediente.findById(id);
        if (ingrediente == null) {
            return null;
        }
        // ajuste conforme seus atributos
        ingrediente.persist();
        return ingrediente;
    }

    @Override
    @Transactional
    public boolean deletar(Long id) {
        return Ingrediente.deleteById(id);
    }
}
