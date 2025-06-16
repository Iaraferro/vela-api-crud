package com.iaramartins.Resource;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;

import com.iaramartins.service.ItemPedidoService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


import static io.restassured.RestAssured.given;

@QuarkusTest
public class ItemPedidoResourceTest {
    @Inject
    ItemPedidoService itemPedidoService;

    private static Long itemId;
    private static Long pedidoId = 1L;
    private static Long velaId = 1L;

    private String token;


    @BeforeEach
    @Transactional
    void setup() {
        // Limpa dados de testes anteriores
        itemPedidoService.listarItensPorPedido(pedidoId).forEach(item -> {
            itemPedidoService.removerItem(item.id());
        });
       

        token = TokenUtils.generateClientToken();
    }

    private ItemPedidoRequestDTO criarRequest(int quantidade) {
        return new ItemPedidoRequestDTO(velaId, quantidade, null);
    }

    @Test
    @Order(1)
    void testAdicionarItem() {
         // Cria o DTO corretamente
         ItemPedidoResponseDTO response = given()
         .contentType(ContentType.JSON)
         .header("Authorization", "Bearer " + token)
         .body(criarRequest(1))
         .when()
         .post("/pedidos/" + pedidoId + "/itens")
         .then()
         .statusCode(201)
         .body("id", notNullValue())
         .body("quantidade", is(1))
         .extract().as(ItemPedidoResponseDTO.class);

     itemId = response.id();
    }

    @Test
    @Order(2)
    void testBuscarItemPorId() {
        ItemPedidoResponseDTO item = itemPedidoService.adicionarItem(pedidoId, criarRequest(1));
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/pedidos/" + pedidoId + "/itens/detalhes/" + item.id())
            .then()
            .statusCode(200)
            .body("quantidade", is(1));
    }

    @Test
    @Order(3)
    void testListarItensComFiltros() {
        itemPedidoService.adicionarItem(pedidoId, criarRequest(3)); // Deve aparecer no filtro
        itemPedidoService.adicionarItem(pedidoId, criarRequest(1));

        given()
        .header("Authorization", "Bearer " + token)
        .queryParam("quantidadeMinima", 3)
            .when()
            .get("/pedidos/" + pedidoId + "/itens/filtros")
            .then()
            .statusCode(200)
            .body("size()", is(1));    
    }

    @Test
    @Order(4)
    void testAtualizarQuantidade() {

        ItemPedidoResponseDTO itemParaAtualizar = itemPedidoService.adicionarItem(
        pedidoId, criarRequest(1));

        Double precoUnitarioAtual = itemParaAtualizar.precoUnitario();
        int novaQuantidade = 2;
        
        given()
        .header("Authorization", "Bearer " + token)
        .queryParam("quantidade", novaQuantidade)
        .when()
        .put("/pedidos/" + pedidoId + "/itens/" + itemParaAtualizar.id())
        .then()
        .statusCode(200)
        .body("quantidade", is(novaQuantidade))
        .body("subtotal", is((float)(precoUnitarioAtual * novaQuantidade)));
    }

    @Test
    @Order(5)
    void testRemoverItem() {
        ItemPedidoResponseDTO item = given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body(criarRequest(1))
        .when()
        .post("/pedidos/" + pedidoId + "/itens")
        .then()
        .statusCode(201)
        .extract().as(ItemPedidoResponseDTO.class);

    // 2. Remove o item - agora esperando 204 (No Content)
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .delete("/pedidos/" + pedidoId + "/itens/" + item.id())
        .then()
        .statusCode(204); // Corrigido para esperar 204

    // 3. Verifica que o item foi removido
    given()
        .header("Authorization", "Bearer " + token)
        .when()
        .get("/pedidos/" + pedidoId + "/itens/detalhes/" + item.id())
        .then()
        .statusCode(404);
    }

    @Test
    @Order(6)
    void testListarItensPorPedido() {
        // Cria um item primeiro
        itemPedidoService.adicionarItem(pedidoId, criarRequest(1));
        itemPedidoService.adicionarItem(pedidoId, criarRequest(2));
        given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/pedidos/" + pedidoId + "/itens/total-itens")
            .then()
            .statusCode(200);
    }

   
}
