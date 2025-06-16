package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.util.List;

import com.iaramartins.dto.AdminRequestDTO;
import com.iaramartins.dto.AdminResponseDTO;
import com.iaramartins.service.AdminService;


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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class AdminResource {

private static final Logger LOG = Logger.getLogger(AdminResource.class);

    @Inject
    AdminService adminService;

    @GET
    @Path("/dashboard")
    public Response dashboard(){
        LOG.info(" Método AdminResource.dashboard() chamado");
        return Response.ok("Área do Admin").build();
    }

    // CRUD de Administradores
    @POST
    @Transactional
    public Response criarAdmin(AdminRequestDTO dto) {
        LOG.info(" Método AdminResource.criarAdmin() chamado");
        AdminResponseDTO response = adminService.criarAdmin(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        LOG.info("🛠️ Método AdminResource.buscarPorId() chamado");
        AdminResponseDTO response = adminService.buscarPorId(id);
        return Response.ok(response).build();
    }

    @GET
    public Response listarTodos() {
        LOG.info(" Método AdminResource.listarTodos() chamado");
        List<AdminResponseDTO> admins = adminService.listarTodos();
        return Response.ok(admins).build();
    }

    @PUT
    @Path("/{id}/departamento")
    @Transactional
    public Response atualizarDepartamento(
        @PathParam("id") Long id,
        String novoDepartamento
    ) {
        LOG.info(" Método AdminResource.atualizarDepartamento() chamado");
        adminService.atualizarDepartamento(id, novoDepartamento);
        return Response.ok().build();
    }


    @DELETE
    @Path("/clientes/{id}")
    @Transactional
    public Response banirCliente(@PathParam("id") Long clienteId) {
        LOG.info(" Método AdminResource.banirCliente() chamado");
        adminService.banirCliente(clienteId);
        return Response.noContent().build();
    }
}
