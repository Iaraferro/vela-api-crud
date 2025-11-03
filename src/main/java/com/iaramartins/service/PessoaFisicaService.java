package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.PessoaFisica;

public interface PessoaFisicaService {
    List<PessoaFisica> listar();
    PessoaFisica buscarPorId(Long id);
    PessoaFisica salvar(PessoaFisica pessoaFisica);
    PessoaFisica atualizar(Long id, PessoaFisica pessoaFisica);
    boolean deletar(Long id);
}
