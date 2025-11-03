package com.iaramartins.resource;


import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.service.PedidoService;



import jakarta.enterprise.context.ApplicationScoped;
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

import org.jboss.logging.Logger;

@Path("/pedidos")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    private static final Logger LOG = Logger.getLogger(PedidoResource.class);
    
    @Inject
    PedidoService pedidoService;

    @POST
    @Transactional
    public Response criar(PedidoRequestDTO dto) {
        LOG.info("➡️ Criando pedido para cliente ID: " + dto.clienteId());
        System.out.println("ENTROU NO METODO CRIAR");
        PedidoResponseDTO pedido = pedidoService.criar(dto); //username
        LOG.info("✅ Pedido criado com sucesso. ID: " + pedido.id());
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }

    @GET
    public Response listarTodos() {
    LOG.info("📋 Listando todos os pedidos");
    // Você precisa criar este método no PedidoService
    return Response.ok(pedidoService.listarTodos()).build();
}

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info("🔍 Buscando pedido pelo ID: " + id);
        return Response.ok(pedidoService.getById(id)).build();
    }

    @GET
    @Path("/cliente/{clienteId}")
    public Response listarPorCliente(@PathParam("clienteId") Long clienteId) {
        LOG.info(" Listando pedidos do cliente ID: " + clienteId);
        return Response.ok(pedidoService.listarPorCliente(clienteId)).build();
    }

    @GET
    @Path("/itens/{pedidoId}")
    public Response listarItensPorPedido(@PathParam("pedidoId") Long pedidoId) {
        LOG.info("📋 Listando itens do pedido ID: " + pedidoId);
        PedidoResponseDTO pedido = pedidoService.getById(pedidoId);
        return Response.ok(pedido.itens()).build();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    public Response atualizarStatus(
    @PathParam("id") Long id, @QueryParam("valor") String novoStatus) {
    LOG.info("🔄 Atualizando status do pedido ID: " + id + " para: " + novoStatus);
    try {
    pedidoService.atualizarStatus(id, novoStatus);
    return Response.ok().build();
    } catch (NotFoundException e) {
        return Response.status(404).entity(e.getMessage()).build();
    } catch (IllegalStateException e) {
        return Response.status(400).entity(e.getMessage()).build();
    }

    }

    @DELETE
    @Path("/{id}")
   // @RolesAllowed("CLIENTE")
    @Transactional
    public Response cancelarPedido(@PathParam("id") Long id) {
        LOG.info(" Cancelando pedido ID: " + id);
    try {
        pedidoService.cancelarPedido(id);
        return Response.noContent().build(); // 204 No Content
    } catch (NotFoundException e) {
         LOG.error(" Pedido não encontrado: " + e.getMessage());
        return Response.status(404).entity(e.getMessage()).build();
    } catch (IllegalStateException e) {
        LOG.error(" Erro ao cancelar pedido: " + e.getMessage());
        return Response.status(400).entity(e.getMessage()).build();
    }
}
}
