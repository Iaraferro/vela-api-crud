package com.iaramartins.repository;


import java.util.List;

import com.iaramartins.model.Pagamento;
import com.iaramartins.model.Pedido;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    
    public List<Pagamento> findByStatus(String status) {
        return find("status", status).list();
    }
    
    // Busca pagamentos por método (Pix, Cartão, Boleto)
    public List<Pagamento> findByMetodo(String metodo) {
        return find("metodo", metodo).list();
    }
    
    // Busca pagamentos associados a um pedido específico
    public Pagamento findByPedido(Pedido pedido) {
        return find("pedido", pedido).firstResult();
    }
    
    
}
