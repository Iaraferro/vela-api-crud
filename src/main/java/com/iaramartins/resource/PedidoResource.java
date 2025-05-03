package com.iaramartins.resource;


import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.service.PedidoService;

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

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {
    
    @Inject
    PedidoService pedidoService;

    @POST
    @Transactional
    public Response criar(PedidoRequestDTO dto) {
        PedidoResponseDTO pedido = pedidoService.criar(dto);
        return Response.status(Response.Status.CREATED).entity(pedido).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        return Response.ok(pedidoService.getById(id)).build();
    }

    @GET
    @Path("/cliente/{clienteId}")
    public Response listarPorCliente(@PathParam("clienteId") Long clienteId) {
        return Response.ok(pedidoService.listarPorCliente(clienteId)).build();
    }

    @GET
    @Path("/itens/{pedidoId}")
    public Response listarItensPorPedido(@PathParam("pedidoId") Long pedidoId) {
        PedidoResponseDTO pedido = pedidoService.getById(pedidoId);
        return Response.ok(pedido.itens()).build();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    public Response atualizarStatus(
    @PathParam("id") Long id, @QueryParam("valor") String novoStatus) {
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
    @Transactional
    public Response cancelarPedido(@PathParam("id") Long id) {
    try {
        pedidoService.cancelarPedido(id);
        return Response.noContent().build(); // 204 No Content
    } catch (NotFoundException e) {
        return Response.status(404).entity(e.getMessage()).build();
    } catch (IllegalStateException e) {
        return Response.status(400).entity(e.getMessage()).build();
    }
}
}
