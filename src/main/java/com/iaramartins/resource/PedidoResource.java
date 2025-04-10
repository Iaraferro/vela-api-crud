package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.dto.PedidoRequestDTO;
import com.iaramartins.dto.PedidoResponseDTO;
import com.iaramartins.service.PedidoService;

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
    public PedidoResponseDTO buscarPorId(@PathParam("id") Long id) {
        return pedidoService.getById(id);
    }

    @GET
    @Path("/cliente/{clienteId}")
    public List<PedidoResponseDTO> listarPorCliente(@PathParam("clienteId") Long clienteId) {
        return pedidoService.listarPorCliente(clienteId);
    }
}
