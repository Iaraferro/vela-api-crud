package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.model.Telefone;
import com.iaramartins.service.TelefoneService;

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


@Path("/telefones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneService telefoneService;

    @GET
    public List<Telefone> listar() {
        return telefoneService.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        Telefone telefone = telefoneService.buscarPorId(id);
        if (telefone == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(telefone).build();
    }

    @POST
    @Transactional
    public Response salvar(Telefone telefone) {
        Telefone novo = telefoneService.salvar(telefone);
        return Response.status(Response.Status.CREATED).entity(novo).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Telefone telefone) {
        Telefone atualizado = telefoneService.atualizar(id, telefone);
        if (atualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(atualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        boolean removido = telefoneService.deletar(id);
        if (!removido) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
