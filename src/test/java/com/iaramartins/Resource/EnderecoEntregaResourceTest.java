package com.iaramartins.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import com.iaramartins.dto.EnderecoEntregaRequestDTO;
import com.iaramartins.dto.EnderecoEntregaResponseDTO;
import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.service.PedidoService;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response.Status;




@QuarkusTest
public class EnderecoEntregaResourceTest {
    @Inject
    PedidoService pedidoService;

    private static Long enderecoId;
    private static Long pedidoId;
    private String token;

    
    @BeforeEach
    public void setUp() {
        RestAssured.defaultParser = Parser.JSON;
        token = TokenUtils.generateClientToken();
        
        // Cria um pedido para ser usado nos testes
        PedidoRequestDTO pedido = new PedidoRequestDTO(
            1L, // clienteId
            List.of(new PedidoRequestDTO.ItemPedidoRequestDTO(1L, 1)) // itens
        );
        pedidoId = pedidoService.criar(pedido).id();
    }

    @Test
    @Order(1)
    public void testCriarEnderecoEntrega() {
        EnderecoEntregaRequestDTO dto = new EnderecoEntregaRequestDTO(
            "Rua Teste",
            "123",
            "Apto 101",
            "Centro",
            "São Paulo",
            "SP",
            "01001000",
            pedidoId
        );

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(dto)
            .when()
            .post("/enderecos-entrega")
            .then()
            .statusCode(Status.CREATED.getStatusCode())
            .extract()
            .response();

        EnderecoEntregaResponseDTO enderecoResponse = response.as(EnderecoEntregaResponseDTO.class);
        assertNotNull(enderecoResponse);
        assertNotNull(enderecoResponse.id());
        assertEquals("Rua Teste", enderecoResponse.rua());
        assertEquals("123", enderecoResponse.numero());
        assertEquals("Apto 101", enderecoResponse.complemento());
        assertEquals("Centro", enderecoResponse.bairro());
        assertEquals("São Paulo", enderecoResponse.cidade());
        assertEquals("SP", enderecoResponse.estado());
        assertEquals("01001000", enderecoResponse.cep());

        enderecoId = enderecoResponse.id();
    }

    @Test
    @Order(2)
    public void testListarEnderecosEntrega() {
        // Primeiro cria um endereço
        criarEnderecoTeste();

        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/enderecos-entrega")
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        List<EnderecoEntregaResponseDTO> enderecos = response.jsonPath().getList(".", EnderecoEntregaResponseDTO.class);
        assertTrue(enderecos.size() >= 1, "Deveria retornar pelo menos um endereço");
    }

    @Test
    @Order(3)
    public void testBuscarEnderecoPorId() {
        // Primeiro cria um endereço
        criarEnderecoTeste();

        Response response = RestAssured.given()
            .header("Authorization", "Bearer " + token)
            .when()
            .get("/enderecos-entrega/" + enderecoId)
            .then()
            .statusCode(Status.OK.getStatusCode())
            .extract()
            .response();

        EnderecoEntregaResponseDTO enderecoResponse = response.as(EnderecoEntregaResponseDTO.class);
        assertNotNull(enderecoResponse);
        assertEquals(enderecoId, enderecoResponse.id());
    }

    @Test
    @Order(4)
    public void testDeletarEndereco() {


        // Primeiro cria um endereço para deletar
        criarEnderecoTeste();

         // Token de admin para deletar
        String adminToken = TokenUtils.generateAdminToken();


        // Verifica se existe antes de deletar
        RestAssured.given()
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/enderecos-entrega/" + enderecoId)
            .then()
            .statusCode(Status.OK.getStatusCode());
        
        // Deleta
        RestAssured.given()
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .delete("/enderecos-entrega/" + enderecoId)
            .then()
            .statusCode(Status.NO_CONTENT.getStatusCode());
        
        // Verifica se foi realmente deletado
        RestAssured.given()
            .header("Authorization", "Bearer " + adminToken)
            .when()
            .get("/enderecos-entrega/" + enderecoId)
            .then()
            .statusCode(Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(5)
    public void testCriarEnderecoComPedidoInvalido() {
        EnderecoEntregaRequestDTO dto = new EnderecoEntregaRequestDTO(
            "Rua Teste",
            "123",
            "Apto 101",
            "Centro",
            "São Paulo",
            "SP",
            "01001000",
            99999L // Pedido que não existe
        );

        RestAssured.given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(dto)
            .when()
            .post("/enderecos-entrega")
            .then()
            .statusCode(Status.BAD_REQUEST.getStatusCode());
    }

    private void criarEnderecoTeste() {
        EnderecoEntregaRequestDTO dto = new EnderecoEntregaRequestDTO(
            "Rua Teste",
            "123",
            "Apto 101",
            "Centro",
            "São Paulo",
            "SP",
            "01001000",
            pedidoId
        );

        Response response = RestAssured.given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body(dto)
            .post("/enderecos-entrega");

        enderecoId = response.as(EnderecoEntregaResponseDTO.class).id();
    }
}
