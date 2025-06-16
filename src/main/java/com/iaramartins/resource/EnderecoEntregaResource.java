package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.util.List;

import com.iaramartins.service.EnderecoEntregaService;
import com.iaramartins.dto.EnderecoEntregaRequestDTO;
import com.iaramartins.dto.EnderecoEntregaResponseDTO;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/enderecos-entrega")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoEntregaResource {
    private static final Logger LOG = Logger.getLogger(EnderecoEntregaResource.class);

    @Inject
    EnderecoEntregaService service;

    @POST
    @RolesAllowed({"CLIENTE", "ADMIN"})
    public Response criar(@Valid EnderecoEntregaRequestDTO dto) {
        LOG.info(" Método EnderecoEntregaResource.criar() chamado");
        EnderecoEntregaResponseDTO endereco = service.criar(dto);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    @GET
    @RolesAllowed({"CLIENTE", "ADMIN"})
    public List<EnderecoEntregaResponseDTO> listarTodos() {
        LOG.info("Método EnderecoEntregaResource.listarTodos() chamado");
        return service.listarTodos();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"CLIENTE", "ADMIN"})
    public EnderecoEntregaResponseDTO buscar(@PathParam("id") Long id) {
        LOG.info(" Método EnderecoEntregaResource.buscar() chamado");
        return service.buscarPorId(id);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response deletar(@PathParam("id") Long id) {
        LOG.info(" Método EnderecoEntregaResource.deletar() chamado");
        service.deletar(id);
        return Response.noContent().build();
    }
}
