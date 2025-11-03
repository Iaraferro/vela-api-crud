package com.iaramartins.service;

import java.util.List;

import com.iaramartins.model.PessoaFisica;
import com.iaramartins.repository.PessoaFisicaRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaFisicaServiceImpl implements PessoaFisicaService {

    @Inject
    PessoaFisicaRepository repository;
    
    @Override
    public List<PessoaFisica> listar() {
        return PessoaFisica.listAll();
    }

    @Override
    public PessoaFisica buscarPorId(Long id) {
        return PessoaFisica.findById(id);
    }

    @Override
    @Transactional
    public PessoaFisica salvar(PessoaFisica pessoaFisica) {
        pessoaFisica.persist();
        return pessoaFisica;
    }

    @Override
    @Transactional
    public PessoaFisica atualizar(Long id, PessoaFisica pessoaFisicaAtualizada) {
        PessoaFisica pessoa = PessoaFisica.findById(id);
        if (pessoa == null) {
            return null;
        }
        pessoa.setNome(pessoaFisicaAtualizada.getNome());
        pessoa.setCpf(pessoaFisicaAtualizada.getCpf()); // supondo que tem CPF
        pessoa.persist();
        return pessoa;
    }

    @Override
    @Transactional
    public boolean deletar(Long id) {
        return PessoaFisica.deleteById(id);
    }
}
