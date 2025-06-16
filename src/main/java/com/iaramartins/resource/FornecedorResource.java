package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.util.List;

import com.iaramartins.dto.FornecedorRequestDTO;
import com.iaramartins.dto.FornecedorResponseDTO;
import com.iaramartins.service.FornecedorService;


import jakarta.ws.rs.core.Response.Status;
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



@RolesAllowed("ADMIN")
@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {
    private static final Logger LOG = Logger.getLogger(FornecedorResource.class);


    @Inject
    FornecedorService service;

    @POST
    @Transactional
    public Response criar(FornecedorRequestDTO dto) {
        LOG.info(" Método FornecedorResource.criar() chamado");
        FornecedorResponseDTO response = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public List<FornecedorResponseDTO> listar() {
        LOG.info(" Método FornecedorResource.listar() chamado");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info("Método FornecedorResource.buscarPorId() chamado");
        FornecedorResponseDTO fornecedor = service.buscarPorId(id);
        if (fornecedor == null) {
            LOG.info(" Método FornecedorResource.buscarPorId() - fornecedor não encontrado");
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(fornecedor).build();
    }

    @GET
    @Path("/buscar")
    public Response buscarPorNome(@QueryParam("nome") String nome) {
    LOG.info("Método FornecedorResource.buscarPorNome() chamado");
    if (nome == null || nome.isBlank()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity("O parâmetro 'nome' é obrigatório")
            .build();
    }
    return Response.ok(service.buscarPorNome(nome)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public FornecedorResponseDTO atualizar(@PathParam("id") Long id, FornecedorRequestDTO dto) {
        LOG.info(" Método FornecedorResource.atualizar() chamado");
        return service.atualizar(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        LOG.info(" Método FornecedorResource.deletar() chamado");
        service.deletar(id);
        return Response.noContent().build();
    }
}
