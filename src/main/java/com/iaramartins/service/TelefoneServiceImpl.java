package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Telefone;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {
    @Override
    public List<Telefone> listar() {
        return Telefone.listAll();
    }

    @Override
    public Telefone buscarPorId(Long id) {
        return Telefone.findById(id);
    }

    @Override
    @Transactional
    public Telefone salvar(Telefone telefone) {
        telefone.persist();
        return telefone;
    }

    @Override
    @Transactional
    public Telefone atualizar(Long id, Telefone telefoneAtualizado) {
        Telefone telefone = Telefone.findById(id);
        if (telefone == null) {
            return null;
        }
        telefone.setNumero(telefoneAtualizado.getNumero());
        telefone.setCodigoArea(telefoneAtualizado.getCodigoArea());
        telefone.persist();
        return telefone;
    }

    @Override
    @Transactional
    public boolean deletar(Long id) {
        return Telefone.deleteById(id);
    }
}
