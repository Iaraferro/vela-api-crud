package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.dto.FornecedorRequestDTO;
import com.iaramartins.dto.FornecedorResponseDTO;
import com.iaramartins.service.FornecedorService;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {
    @Inject
    FornecedorService service;

    @POST
    @Transactional
    public Response criar(FornecedorRequestDTO dto) {
        FornecedorResponseDTO response = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public List<FornecedorResponseDTO> listar() {
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public FornecedorResponseDTO buscarPorId(@PathParam("id") Long id) {
        return service.buscarPorId(id);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public FornecedorResponseDTO atualizar(@PathParam("id") Long id, FornecedorRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        service.deletar(id);
        return Response.noContent().build();
    }
}
