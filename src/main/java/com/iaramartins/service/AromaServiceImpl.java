package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Aroma;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AromaServiceImpl implements AromaService{

    @Override
    public List<Aroma> listarAroma() {
        return Aroma.listAll();
    }

    @Override
    public Aroma buscarPorId(Long id) {
        return Aroma.findById(id);
    }

    @Override
    @Transactional
    public Aroma salvar(Aroma aroma) {
        aroma.persist();
        return aroma;
    }

    @Override
    @Transactional
    public Aroma atualizar(Long id, Aroma aromaAtualizado) {
        Aroma aroma = Aroma.findById(id);
        if (aroma == null) {
            return null;
        }
        aroma.setEssenciaAromatica(aromaAtualizado.getEssenciaAromatica());
        return aroma;
    }

    @Override
    @Transactional
    public boolean deletar(Long id) {
        return Aroma.deleteById(id);
    }
}
