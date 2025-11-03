package com.iaramartins.resource;

import java.util.List;

import com.iaramartins.model.PessoaFisica;
import com.iaramartins.service.PessoaFisicaService;

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


@Path("/pessoas-fisicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {
    @Inject
    PessoaFisicaService pessoaFisicaService;

    @GET
    public List<PessoaFisica> listar() {
        return pessoaFisicaService.listar();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        PessoaFisica pessoa = pessoaFisicaService.buscarPorId(id);
        if (pessoa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(pessoa).build();
    }

    @POST
    @Transactional
    public Response salvar(PessoaFisica pessoaFisica) {
        PessoaFisica nova = pessoaFisicaService.salvar(pessoaFisica);
        return Response.status(Response.Status.CREATED).entity(nova).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, PessoaFisica pessoaFisica) {
        PessoaFisica atualizada = pessoaFisicaService.atualizar(id, pessoaFisica);
        if (atualizada == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(atualizada).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        boolean removida = pessoaFisicaService.deletar(id);
        if (!removida) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
