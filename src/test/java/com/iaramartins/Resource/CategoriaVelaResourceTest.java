package com.iaramartins.Resource;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.iaramartins.dto.CategoriaVelaRequestDTO;
import com.iaramartins.dto.CategoriaVelaResponseDTO;
import com.iaramartins.service.CategoriaVelaService;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;



import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import io.restassured.http.ContentType;


import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class CategoriaVelaResourceTest {
    @Inject
    CategoriaVelaService categoriaVelaService;

    private String token;

    @BeforeEach
    void setUp() {
        // Gera token de autenticação antes de cada teste
        token = TokenUtils.generateAdminToken();
    }

    @Test
    @Order(1)
    void testListarTodos() {
        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/categorias")
            .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void testCriar() {
        CategoriaVelaRequestDTO categoria = new CategoriaVelaRequestDTO(
            "Categoria Teste", 
            "Descrição da categoria teste");

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(categoria)
            .when().post("/categorias")
            .then()
                .statusCode(201)
                .body(
                    "id", notNullValue(),
                    "nome", is("Categoria Teste"),
                    "descricao", is("Descrição da categoria teste") 
                );
    }

    static Long id = null;

    @Test
    @Order(3)
    void testAtualizar() {
        // Cria uma categoria primeiro
        CategoriaVelaRequestDTO categoria = new CategoriaVelaRequestDTO(
            "Categoria Original", 
            "Descrição original");
       
        id = categoriaVelaService.criar(categoria).id();

        // Dados para atualização
        CategoriaVelaRequestDTO categoriaAtualizada = new CategoriaVelaRequestDTO(
            "Categoria Atualizada", 
            "Nova descrição");

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(categoriaAtualizada)
            .when().put("/categorias/" + id)
            .then()
                .statusCode(204);

        // Verifica se a atualização foi feita
        CategoriaVelaResponseDTO response = categoriaVelaService.buscarPorId(id);
        assertThat(response.nome(), is("Categoria Atualizada"));
        assertThat(response.descricao(), is("Nova descrição"));
    }

    @Test
    @Order(4)
    void testDeletar() {
        // Cria uma categoria para deletar
        CategoriaVelaRequestDTO categoria = new CategoriaVelaRequestDTO(
            "Categoria para Deletar", 
            "Descrição para deletar");
       
        Long idDeletar = categoriaVelaService.criar(categoria).id();

        given()
            .header("Authorization", "Bearer " + token)
            .when().delete("/categorias/" + idDeletar)
            .then()
                .statusCode(204);

        // Verifica se foi deletado
        try {
            CategoriaVelaResponseDTO response = categoriaVelaService.buscarPorId(idDeletar);
            assertNull(response);
        } catch (Exception e) {
            // Esperado que lance exceção quando não encontrar
            assertThat(e.getMessage(), is("Categoria não encontrada"));
        }
    }

    @Test
    @Order(5)
    void testBuscarPorNome() {
        // Cria algumas categorias para testar a busca
        CategoriaVelaResponseDTO categoriaBusca = categoriaVelaService.criar(
            new CategoriaVelaRequestDTO("Busca Específica", "Descrição busca"));

        given()
            .header("Authorization", "Bearer " + token)
            .queryParam("nome", "Específica")
            .when().get("/categorias/buscar")
            .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].nome", is("Busca Específica"));
    }

    @Test
    @Order(6)
    void testBuscarPorId() {
        // Cria uma categoria para buscar
        CategoriaVelaResponseDTO categoria = categoriaVelaService.criar(
            new CategoriaVelaRequestDTO("Buscar por ID", "Descrição para buscar"));

        given()
            .header("Authorization", "Bearer " + token)
            .when().get("/categorias/" + categoria.id())
            .then()
                .statusCode(200)
                .body(
                    "id", is(categoria.id().intValue()),
                    "nome", is("Buscar por ID"),
                    "descricao", is("Descrição para buscar")
                );
    }
}
