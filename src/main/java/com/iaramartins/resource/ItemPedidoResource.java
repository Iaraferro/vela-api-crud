package com.iaramartins.resource;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.service.ItemPedidoService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/pedidos/{pedidoId}/itens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {
    @Inject
    ItemPedidoService itemPedidoService;

    @POST
    @Transactional
    public Response adicionarItem(@PathParam("pedidoId") Long pedidoId, ItemPedidoRequestDTO dto) {
        return Response.status(201)
            .entity(itemPedidoService.adicionarItem(pedidoId, dto))
            .build();
    }

    @GET
    public Response listarItens(@PathParam("pedidoId") Long pedidoId) {
        return Response.ok(itemPedidoService.listarItensPorPedido(pedidoId))
            .build();
    }
}
