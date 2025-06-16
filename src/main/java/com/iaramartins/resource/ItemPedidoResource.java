package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.util.List;

import com.iaramartins.dto.ItemPedidoRequestDTO;
import com.iaramartins.dto.ItemPedidoResponseDTO;
import com.iaramartins.model.TipoVela;
import com.iaramartins.service.ItemPedidoService;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
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

@Authenticated
@Path("/pedidos/{pedidoId}/itens")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemPedidoResource {
    private static final Logger LOG = Logger.getLogger(ItemPedidoResource.class);

    @Inject
    ItemPedidoService itemPedidoService;

    @POST
    @Transactional
    public Response adicionarItem(@PathParam("pedidoId") Long pedidoId, ItemPedidoRequestDTO dto) {
        LOG.info(" Método ItemPedidoResource.adicionarItem() chamado");
        return Response.status(201)
            .entity(itemPedidoService.adicionarItem(pedidoId, dto))
            .build();
    }

    @GET
    @Path("/total-itens")
    public Response listarItens(@PathParam("pedidoId") Long pedidoId) {
        LOG.info(" Método ItemPedidoResource.listarItens() chamado");
        return Response.ok(itemPedidoService.listarItensPorPedido(pedidoId))
            .build();
    }

    
    @GET
    @Path("/detalhes/{itemId}")
    @RolesAllowed("CLIENTE")
    public Response buscarItemPorId(
        @PathParam("pedidoId") Long pedidoId,
        @PathParam("itemId") Long itemId
    ) {
        LOG.info("📦 Método ItemPedidoResource.buscarItemPorId() chamado");
        return Response.ok(itemPedidoService.buscarItemPorId(itemId))
            .build();
    }

    @GET
    @Path("/filtros")
    public Response listarItensComFiltros(
    @PathParam("pedidoId") Long pedidoId, @QueryParam("tipoVela") TipoVela tipoVela,  
    @QueryParam("quantidadeMinima") Integer quantidadeMinima  
    ) {
      LOG.info("📦 Método ItemPedidoResource.listarItensComFiltros() chamado");
      List<ItemPedidoResponseDTO> itensFiltrados = itemPedidoService
        .listarItensPorPedidoComFiltros(pedidoId, tipoVela, quantidadeMinima);
    
    return Response.ok(itensFiltrados).build();
    }

    // 2. PUT para atualizar a quantidade de um item (ex: /pedidos/1/itens/2)
    @PUT
    @Path("/{itemId}")
    @RolesAllowed("CLIENTE")
    @Transactional
    public Response atualizarQuantidade(@PathParam("pedidoId") Long pedidoId,@PathParam("itemId") Long itemId, @QueryParam("quantidade") int novaQuantidade
    ) {
        LOG.info(" Método ItemPedidoResource.atualizarQuantidade() chamado");
        return Response.ok(
            itemPedidoService.atualizarQuantidade(itemId, novaQuantidade)
        ).build();
    }

    // 3. DELETE para remover um item do pedido (ex: /pedidos/1/itens/2)
    @DELETE
    @Path("/{itemId}")
    @RolesAllowed("CLIENTE")
    @Transactional
    public Response removerItem(@PathParam("pedidoId") Long pedidoId, @PathParam("itemId") Long itemId
    ) {
        LOG.info("Método ItemPedidoResource.removerItem() chamado");
        itemPedidoService.removerItem(itemId);
        return Response.noContent().build(); // HTTP 204 (No Content)
    }


}
