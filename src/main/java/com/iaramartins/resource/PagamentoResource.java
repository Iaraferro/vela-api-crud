package com.iaramartins.resource;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;
import com.iaramartins.service.PagamentoService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {

    @Inject
    PagamentoService pagamentoService;
    
    //"Quero pagar meu pedido!" (Recebe os dados do pagamento)
    @POST
    public Response pagar(PagamentoRequestDTO dto) {
        PagamentoResponseDTO resposta = pagamentoService.criar(dto);
        return Response.ok(resposta).build(); // Retorna confirmação
    }

    // "Qual o status do meu pagamento?" (Busca por ID)
    @GET
    @Path("/{id}")
    public Response consultarStatus(@PathParam("id") Long id) {
        PagamentoResponseDTO resposta = pagamentoService.buscarPorId(id);
        return Response.ok(resposta).build();
    }

    //"Atualizar status" (Usado pelo sistema de pagamento)
    @PUT
    @Path("/{id}/aprovar")
    public Response aprovarPagamento(@PathParam("id") Long id) {
        pagamentoService.atualizarStatus(id, "APROVADO");
        return Response.ok("Pagamento aprovado!").build();
    }
    
}
