package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.Aroma;

public interface AromaService {
    List<Aroma> listarAroma();
    Aroma buscarPorId(Long id);
    Aroma salvar(Aroma aroma);
    Aroma atualizar(Long id, Aroma aroma);
    boolean deletar(Long id);
}
