package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.dto.CategoriaVelaRequestDTO;
import com.iaramartins.dto.CategoriaVelaResponseDTO;
import com.iaramartins.service.CategoriaVelaService;

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

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaVelaResource {
    
    @Inject
    CategoriaVelaService service;

    @POST
    @Transactional
    public Response criar(CategoriaVelaRequestDTO dto){
        CategoriaVelaResponseDTO response = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public List<CategoriaVelaResponseDTO> listarTodos(){
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public CategoriaVelaResponseDTO buscarPorId(@PathParam("id") Long id){
        return service.buscarPorId(id);
    }
    @GET
    @Path("/buscar")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
        if (nome == null || nome.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Parâmetro 'nome' é obrigatório")
                .build();
        }
        return Response.ok(service.buscarPorNome(nome)).build();
    }

     @PUT
    @Path("/{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, CategoriaVelaRequestDTO dto) {
        service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        service.deletar(id);
    }
}
