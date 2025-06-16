package com.iaramartins.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;
import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.service.PagamentoService;
import com.iaramartins.service.PedidoService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class PagamentoResourceTest {
    @Inject
    PedidoService pedidoService;

    @Inject
    PagamentoService pagamentoService;

   private Long pedidoIdTeste;
   private String token;

    @BeforeEach
    @Transactional
    void setUp() {
        // Gera token de autenticação
        token = TokenUtils.generateAdminToken();

        // Cria um novo pedido para cada teste
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L, // clienteId
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2)) // itens
        );
        pedidoIdTeste = pedidoService.criar(pedido).id();
    }

    @Test
    void testCriarPagamento() {
        PagamentoRequestDTO request = new PagamentoRequestDTO(
            pedidoIdTeste,
            "PIX",
            new BigDecimal("150.00")
        );

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(request)
            .when()
            .post("/pagamentos") // POST /pagamentos
            .then()
            .statusCode(200)
            .body("id", notNullValue())
            .body("pedidoId", equalTo(pedidoIdTeste.intValue()))
            .body("status", equalTo("PENDENTE"));
    }

    @Test
    void testConsultarStatus() {
        // Primeiro cria o pagamento
        PagamentoResponseDTO pagamento = criarPagamentoTeste();
        
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/pagamentos/" + pagamento.id()) // GET /pagamentos/{id}
            .then()
            .statusCode(200)
            .body("id", equalTo(pagamento.id().intValue()));
    }

    @Test
    void testAprovarPagamento() {
        PagamentoResponseDTO pagamento = criarPagamentoTeste();
        
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .put("/pagamentos/" + pagamento.id() + "/aprovar") // PUT /pagamentos/{id}/aprovar
            .then()
            .statusCode(200);

        // Verifica se o status foi atualizado
        PagamentoResponseDTO response = pagamentoService.buscarPorId(pagamento.id());
        assertEquals("APROVADO", response.status());
    }

    @Test
    void testBuscarPorPedidoId() {
        PagamentoResponseDTO pagamento = criarPagamentoTeste();
        
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/pagamentos/pedido/" + pagamento.pedidoId()) // GET /pagamentos/pedido/{pedidoId}
            .then()
            .statusCode(200)
            .body("id", equalTo(pagamento.id().intValue()));
    }

    @Test
    void testDeletarPagamento() {
        PagamentoResponseDTO pagamento = criarPagamentoTeste();
        
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .delete("/pagamentos/" + pagamento.id()) // DELETE /pagamentos/{id}
            .then()
            .statusCode(204);

        // Verifica se foi realmente removido
        assertThrows(NotFoundException.class, () -> {
            pagamentoService.buscarPorId(pagamento.id());
        });
    }

    @Test
    void testCriarSegundoPagamentoParaMesmoPedido() {
        // Primeiro pagamento (sucesso)
        criarPagamentoTeste();
        
        // Segundo pagamento (deve falhar)
        PagamentoRequestDTO segundoPagamento = new PagamentoRequestDTO(
            pedidoIdTeste,
            "CARTAO",
            new BigDecimal("50.00")
        );
        
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(segundoPagamento)
            .when()
            .post("/pagamentos")
            .then()
            .statusCode(400)
            .body(equalTo("Este pedido já possui um pagamento registrado")); 
    }

    private PagamentoResponseDTO criarPagamentoTeste() {
        PagamentoRequestDTO request = new PagamentoRequestDTO(
            pedidoIdTeste,
            "CARTAO",
            new BigDecimal("100.00")
        );
        return pagamentoService.criar(request);
    }
}
