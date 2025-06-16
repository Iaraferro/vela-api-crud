package com.iaramartins.resource;

import org.jboss.logging.Logger;

import java.util.List;

import com.iaramartins.dto.PagamentoRequestDTO;
import com.iaramartins.dto.PagamentoResponseDTO;
import com.iaramartins.service.PagamentoService;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Authenticated
@Path("/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PagamentoResource {
    private static final Logger LOG = Logger.getLogger(PagamentoResource.class);

    @Inject
    PagamentoService pagamentoService;
    
    //"Quero pagar meu pedido!" (Recebe os dados do pagamento)
    @POST
    public Response pagar(PagamentoRequestDTO dto) {
       LOG.info(" Método PagamentoResource.pagar() chamado");
       try {
        PagamentoResponseDTO resposta = pagamentoService.criar(dto);
        return Response.ok(resposta).build();
    } catch (IllegalStateException e) {
        return Response.status(Response.Status.BAD_REQUEST)
                      .entity(e.getMessage())
                      .build();
    } catch (NotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                      .entity(e.getMessage())
                      .build();
    }
    }

    // "Qual o status do meu pagamento?" (Busca por ID)
    @GET
    @Path("/{id}")
    public Response consultarStatus(@PathParam("id") Long id) {
        LOG.info(" Método PagamentoResource.consultarStatus() chamado");
        PagamentoResponseDTO resposta = pagamentoService.buscarPorId(id);
        return Response.ok(resposta).build();
    }

    //"Atualizar status" (Usado pelo sistema de pagamento)
    @PUT
    @Path("/{id}/aprovar")
    @RolesAllowed("ADMIN")
    public Response aprovarPagamento(@PathParam("id") Long id) {
        LOG.info(" Método PagamentoResource.aprovarPagamento() chamado");
        pagamentoService.atualizarStatus(id, "APROVADO");
        return Response.ok("Pagamento aprovado!").build();
    }

    @GET
    @RolesAllowed("ADMIN")
    public Response listarTodos(@QueryParam("status") String status) {
        LOG.info(" Método PagamentoResource.listarTodos() chamado");
        List<PagamentoResponseDTO> pagamentos = pagamentoService.listarTodos(status);
        return Response.ok(pagamentos).build();
    }

    // 2. GET para buscar pagamento por ID do pedido (alternativa ao método existente)
    @GET
    @Path("/pedido/{pedidoId}")
    public Response buscarPorPedido(@PathParam("pedidoId") Long pedidoId) {
        LOG.info(" Método PagamentoResource.buscarPorPedido() chamado");
        PagamentoResponseDTO resposta = pagamentoService.buscarPorPedidoId(pedidoId);
        return Response.ok(resposta).build();
    }
    
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Transactional
    public Response deletarPagamento(@PathParam("id") Long id) {
    LOG.info(" Método PagamentoResource.deletarPagamento() chamado");
    pagamentoService.deletarPagamento(id);
    return Response.noContent().build(); // HTTP 204 (No Content)

    }

    @GET
    @Path("/{pedidoId}/qrcode")
    @Produces("image/png")
    @RolesAllowed({"CLIENTE", "ADMIN"})
    public Response gerarQrCodePagamento(@PathParam("pedidoId") Long pedidoId) {
    LOG.info(" Método PagamentoResource.gerarQrCodePagamento() chamado");
    byte[] imagem = pagamentoService.gerarQrCodePagamento(pedidoId);
    return Response.ok(imagem).build();
    }
}
