package com.iaramartins.Resource;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.hamcrest.CoreMatchers.*;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.iaramartins.dto.PedidoRequestDTO;

import com.iaramartins.service.PedidoService;
import com.iaramartins.dto.PedidoResponseDTO;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;

import static io.restassured.RestAssured.given;



@QuarkusTest
public class PedidoResourceTest {
    @Inject
    PedidoService pedidoService;

    private String token;
    static Long idCriado = null;

    @BeforeEach
    void setUp() {
        // Gera token de autenticação antes de cada teste
        token = TokenUtils.generateClientToken();
    }

    @Test
    void testCriar() {
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L,
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2))
        );

        idCriado = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(pedido)
            .when().post("/pedidos")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "clienteId", is(1),
                    "status", is("PENDENTE"),
                    "itens.size()", is(1)
                )
                .extract().body().jsonPath().getLong("id");
    }

    @Test
    void testBuscarPorId() {
        // Primeiro cria um pedido para testar
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L,
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2))
        );
        idCriado = pedidoService.criar(pedido).id();

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/pedidos/" + idCriado)
            .then()
                .statusCode(200)
                .body(
                    "id", equalTo(idCriado.intValue()),
                    "clienteId", is(1)
                );
    }

    @Test
    void testListarPorCliente() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/pedidos/cliente/1")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
    void testListarItensPorPedido() {
        // Cria os itens do pedido primeiro
    List<PedidoRequestDTO.ItemPedidoRequestDTO> itens = List.of(
        new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2), // velaId, quantidade
        new PedidoRequestDTO.ItemPedidoRequestDTO(2L, 1)
    );

    // Agora cria o pedidoRequest com os itens
    PedidoRequestDTO pedidoRequest = new PedidoRequestDTO(
        1L, // clienteId
        itens
    );
    
    // Persiste o pedido
    PedidoResponseDTO pedidoResponse = pedidoService.criar(pedidoRequest);
    Long pedidoId = pedidoResponse.id();
    
    // Verifica se o pedido foi criado
    assertNotNull(pedidoId, "O ID do pedido não deve ser nulo");
    assertEquals(2, pedidoResponse.itens().size(), "Deve ter 2 itens no pedido");

    // Testa o endpoint
    given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/pedidos/itens/" + pedidoId)
        .then()
        .statusCode(200)
        .body("size()", equalTo(2))
        .body("[0].nomeVela", notNullValue())
        .body("[0].quantidade", equalTo(2))
        .body("[1].quantidade", equalTo(1));
    }

    @Test
    void testAtualizarStatus() {
        // Cria um pedido para testar
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L,
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2))
        );
        Long pedidoId = pedidoService.criar(pedido).id();

        given()
            .header("Authorization", "Bearer " + token)
            .queryParam("valor", "PAGO")
            .when().put("/pedidos/" + pedidoId + "/status")
            .then()
                .statusCode(200);

        PedidoResponseDTO response = pedidoService.getById(pedidoId);
        assertEquals("PAGO", response.status());
    }

    @Test
    void testCancelarPedido() {
        // Cria um pedido para testar
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L,
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2))
        );
        Long pedidoId = pedidoService.criar(pedido).id();

        given()
            .header("Authorization", "Bearer " + token)
            .when().delete("/pedidos/" + pedidoId)
            .then()
                .statusCode(204);

        PedidoResponseDTO response = pedidoService.getById(pedidoId);
        assertEquals("CANCELADO", response.status());
    }

    @Test
    void testBuscarPorIdNaoExistente() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/pedidos/99999")
            .then()
                .statusCode(404);
    }

    @Test
    void testAtualizarStatusPedidoCancelado() {
        // Cria e cancela um pedido
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L,
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 2))
        );
        Long pedidoId = pedidoService.criar(pedido).id();
        pedidoService.cancelarPedido(pedidoId);

        given()
            .header("Authorization", "Bearer " + token)
            .queryParam("valor", "PAGO")
            .when().put("/pedidos/" + pedidoId + "/status")
            .then()
                .statusCode(400);
    }
}
