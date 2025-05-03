package com.iaramartins.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;


import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import com.iaramartins.dto.FornecedorRequestDTO;
import com.iaramartins.dto.FornecedorResponseDTO;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.ws.rs.core.Response.Status;



@QuarkusTest
public class FornecedorResourceTest {

    
    private static Long fornecedorId;
    private static final String CNPJ_TESTE = "12345678901234";
    private static final String TELEFONE_TESTE = "11999999999";

    @Test
    @Order(1)
    public void testCriarFornecedor() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Teste", 
            CNPJ_TESTE, 
            TELEFONE_TESTE
        );

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .post("/fornecedores")
            .then()
            .statusCode(Status.CREATED.getStatusCode())
            .extract()
            .response();

        FornecedorResponseDTO fornecedorResponse = response.as(FornecedorResponseDTO.class);
        assertNotNull(fornecedorResponse);
        assertNotNull(fornecedorResponse.id());
        assertEquals("Fornecedor Teste", fornecedorResponse.nome());
        assertEquals(CNPJ_TESTE, fornecedorResponse.cnpj());
        assertEquals(TELEFONE_TESTE, fornecedorResponse.telefone());

        fornecedorId = fornecedorResponse.id();
    }

    @Test
    @Order(2)
    public void testListarFornecedores() {
        Response response = RestAssured.given()
            .when()
            .get("/fornecedores")
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        List<FornecedorResponseDTO> fornecedores = response.jsonPath().getList(".", FornecedorResponseDTO.class);
        assertFalse(fornecedores.isEmpty(), "Deveria retornar pelo menos um fornecedor");
    }

    @Test
    @Order(3)
    public void testBuscarFornecedorPorId() {
        Response response = RestAssured.given()
            .when()
            .get("/fornecedores/" + fornecedorId)
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        FornecedorResponseDTO fornecedorResponse = response.as(FornecedorResponseDTO.class);
        assertNotNull(fornecedorResponse);
        assertEquals(fornecedorId, fornecedorResponse.id());
    }

    @Test
    @Order(4)
    public void testBuscarFornecedorPorNome() {
        // Primeiro cria um fornecedor com nome específico para buscar
        String nomeBusca = "Fornecedor Especial Busca";
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            nomeBusca, 
            "99999999999999", 
            "11999999999"
        );
        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
            .post("/fornecedores");

        // Agora busca
        Response response = RestAssured.given()
            .when()
            .queryParam("nome", "Especial")
            .get("/fornecedores/buscar")
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        List<FornecedorResponseDTO> fornecedores = response.jsonPath().getList(".", FornecedorResponseDTO.class);
        assertFalse(fornecedores.isEmpty(), "Deveria encontrar pelo menos um fornecedor");
        assertTrue(fornecedores.get(0).nome().contains("Especial"), 
            "Deveria encontrar fornecedor com 'Especial' no nome");
    }

    @Test
    @Order(5)
    public void testAtualizarFornecedor() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Atualizado", 
            "98765432109876", 
            "11888888888"
        );

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when()
            .put("/fornecedores/" + fornecedorId)
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        FornecedorResponseDTO fornecedorResponse = response.as(FornecedorResponseDTO.class);
        assertNotNull(fornecedorResponse);
        assertEquals(fornecedorId, fornecedorResponse.id());
        assertEquals("Fornecedor Atualizado", fornecedorResponse.nome());
        assertEquals("98765432109876", fornecedorResponse.cnpj());
        assertEquals("11888888888", fornecedorResponse.telefone());
    }

    @Test
    @Order(6)
    public void testDeletarFornecedor() {
        FornecedorRequestDTO dto = new FornecedorRequestDTO(
            "Fornecedor Para Deletar", 
            "99999999999999", 
            "11999999999"
        );
        
        Response createResponse = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(dto)
            .post("/fornecedores");
        
        Long idParaDeletar = createResponse.as(FornecedorResponseDTO.class).id();
        
        // Verifica se existe antes de deletar
        RestAssured.given()
            .when()
            .get("/fornecedores/" + idParaDeletar)
            .then()
            .statusCode(Status.OK.getStatusCode());
        
        // Deleta
        RestAssured.given()
            .when()
            .delete("/fornecedores/" + idParaDeletar)
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode());
        
        // Verifica se foi realmente deletado
        RestAssured.given()
            .when()
            .get("/fornecedores/" + idParaDeletar)
            .then()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(7)
    public void testBuscarFornecedorPorNomeSemParametro() {
        RestAssured.given()
            .when()
            .get("/fornecedores/buscar")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }
}
