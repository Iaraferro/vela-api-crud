package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.model.Ingrediente;

import io.quarkus.panache.common.Sort;
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
import jakarta.ws.rs.core.MediaType;

@Path("/ingredientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IgredienteResource {

    @GET
    public List<Ingrediente> listar() {
        return Ingrediente.listAll(Sort.by("id"));
    }

    @GET
    @Path("/{id}")
    public Ingrediente buscarPorId(@PathParam("id") Long id) {
        return Ingrediente.findById(id);
    }

    @POST
    @Transactional
    public Ingrediente adicionar(Ingrediente ingrediente) {
        ingrediente.persist();
        return ingrediente;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Ingrediente atualizar(@PathParam("id") Long id, Ingrediente ingrediente) {
        Ingrediente existente = Ingrediente.findById(id);
        if (existente == null) {
            throw new NotFoundException();
        }
        existente.setRecipiente(ingrediente.getRecipiente());
        existente.setPavio(ingrediente.getPavio());
        existente.setTipoCera(ingrediente.getTipoCera());
        return existente;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        Ingrediente.deleteById(id);
    }
    
}
