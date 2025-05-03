package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;
import com.iaramartins.model.TipoVela;
import com.iaramartins.service.ItemPedidoService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    @Path("/total-itens")
    public Response listarItens(@PathParam("pedidoId") Long pedidoId) {
        return Response.ok(itemPedidoService.listarItensPorPedido(pedidoId))
            .build();
    }

    
    @GET
    @Path("/detalhes/{itemId}")
    public Response buscarItemPorId(
        @PathParam("pedidoId") Long pedidoId,
        @PathParam("itemId") Long itemId
    ) {
        return Response.ok(itemPedidoService.buscarItemPorId(itemId))
            .build();
    }

    @GET
    @Path("/filtros")
    public Response listarItensComFiltros(
    @PathParam("pedidoId") Long pedidoId, @QueryParam("tipoVela") TipoVela tipoVela,  
    @QueryParam("quantidadeMinima") Integer quantidadeMinima  
    ) {
    List<ItemPedidoResponseDTO> itensFiltrados = itemPedidoService
        .listarItensPorPedidoComFiltros(pedidoId, tipoVela, quantidadeMinima);
    
    return Response.ok(itensFiltrados).build();
    }

    // 2. PUT para atualizar a quantidade de um item (ex: /pedidos/1/itens/2)
    @PUT
    @Path("/{itemId}")
    @Transactional
    public Response atualizarQuantidade(@PathParam("pedidoId") Long pedidoId,@PathParam("itemId") Long itemId, @QueryParam("quantidade") int novaQuantidade
    ) {
        return Response.ok(
            itemPedidoService.atualizarQuantidade(itemId, novaQuantidade)
        ).build();
    }

    // 3. DELETE para remover um item do pedido (ex: /pedidos/1/itens/2)
    @DELETE
    @Path("/{itemId}")
    @Transactional
    public Response removerItem(@PathParam("pedidoId") Long pedidoId, @PathParam("itemId") Long itemId
    ) {
        itemPedidoService.removerItem(itemId);
        return Response.noContent().build(); // HTTP 204 (No Content)
    }


}
