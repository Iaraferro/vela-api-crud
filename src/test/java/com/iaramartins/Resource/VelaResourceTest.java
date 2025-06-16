package com.iaramartins.Resource;


import io.restassured.http.ContentType;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;  // Para given()
import static org.hamcrest.CoreMatchers.is;  // Para is()
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.lessThanOrEqualTo;


import io.restassured.response.Response;


import jakarta.inject.Inject;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.model.TipoVela;
import com.iaramartins.model.Vela;
import com.iaramartins.service.VelaService;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class VelaResourceTest {
    @Inject
    VelaService velaService;

    private String token;

    @BeforeEach
    void setUp() {
        token = TokenUtils.generateAdminToken(); 
    }

    @Test
    @Order(1)
    void testCadastrar() {
        VelaRequestDTO dto =  VelaRequestDTO.createWithoutStock(
            "Vela de Proteção Teste",  // Nome único para teste
            39.90,                     // Preço dentro da faixa
            TipoVela.PROTECAO_ESPIRITUAL,
            "Arruda e Alecrim",        // Aroma existente
            "Cera de abelha, carvão",  // Ingredientes
            "Proteção teste"           // Ritual
        );

       Response response = given()
        .contentType(ContentType.JSON)
        .header("Authorization", "Bearer " + token)
        .body(dto)
        .when()
        .post("/velas");
    
    assertEquals(201, response.getStatusCode());
    assertEquals("Vela de Proteção Teste", response.jsonPath().getString("nome"));
    }

    @Test
    @Order(2)
    void testListarDisponiveis() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/disponiveis")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(3)) // Pelo menos 3 velas iniciais
                .body("findAll { it.nome == 'Proteção Ancestral' }.preco", hasItem(35.90f));
    }

    @Test
    @Order(3)
    void testBuscarPorId() {
        // Primeiro busca uma vela existente na base
        Integer idExistente = given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/disponiveis")
            .then()
                .extract().path("[0].id"); // Pega o ID da primeira vela disponível

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/" + idExistente)
            .then()
                .statusCode(200)
                .body("id", is(idExistente.intValue()));
    }

    @Test
    @Order(4)
    void testListarOrdenadasPorPreco() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/ordenadas-por-preco")
            .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(3))
                .body("[0].preco", lessThanOrEqualTo(28.00f)); // A mais barata primeiro
    }

    @Test
    @Order(5)
    void testAtualizar() {
        // Cria uma vela temporária para atualizar
        Long id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body( VelaRequestDTO.createWithoutStock(
                "Vela Temp",
                25.00,
                TipoVela.LIMPEZA_ENERGETICA,
                "Limão",
                "Cera",
                "Teste"
            ))
            .when().post("/velas")
            .then()
            .statusCode(201)
                .extract().jsonPath().getLong("id");

        VelaRequestDTO atualizacao = VelaRequestDTO.createWithoutStock(
            "Vela Atualizada",
            45.00,
            TipoVela.PROTECAO_ESPIRITUAL,
            "Novo Aroma",
            "Novos Ingredientes",
            "Novo Ritual"
        );

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(atualizacao)
            .header("Authorization", "Bearer " + token)
            .when().put("/velas/" + id)
            .then()
                .statusCode(204);

        // Verifica se atualizou
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/" + id)
            .then()
                .body("nome", is("Vela Atualizada"))
                .body("preco", is(45.00f));
    }

    @Test
    @Order(6)
    void testDeletar() {
        // Cria uma vela temporária para deletar
        Long id = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(VelaRequestDTO.createWithoutStock(
                "Vela para Deletar",
                15.00,
                TipoVela.LIMPEZA_ENERGETICA,
                "Eucalipto",
                "Cera pura",
                "Teste"
            ))
            .when().post("/velas")
            .then()
                .extract().jsonPath().getLong("id");

        given()
            .header("Authorization", "Bearer " + token)
            .when().delete("/velas/" + id)
            .then()
                .statusCode(204);

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/velas/" + id)
            .then()
                .statusCode(404);
    }
}
