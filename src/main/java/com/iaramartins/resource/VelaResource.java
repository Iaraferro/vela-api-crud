package com.iaramartins.resource;


import java.util.List;
import java.util.Map;
import org.jboss.logging.Logger;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;
import com.iaramartins.service.VelaService;


import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.PermitAll;



 @Path("/velas")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)
 public class VelaResource {
    private static final Logger LOG = Logger.getLogger(VelaResource.class);

    @Inject
    VelaService velaService;

    @POST
    @RolesAllowed("ADMIN")
    @Transactional
    public Response cadastrar(VelaRequestDTO dto) {
        LOG.info(" Método VelaResource.cadastrar() chamado");
        return Response
            .status(201)
            .entity(velaService.cadastrar(dto))
            .build();
    }

    @GET
    @Path("/disponiveis")
    @PermitAll
    public List<VelaResponseDTO> disponiveis() {
        //LOG.info(" Método VelaResource.disponiveis() chamado");
        return velaService.listarDisponiveis();
    }

    //Buscar vela pelo id (GET)
    @GET
    @Path("/{id}")
    public VelaResponseDTO buscarPorId(@PathParam("id") Long id) {
        LOG.info(" Método VelaResource.buscarPorId() chamado");
        return velaService.getById(id);
    }

    @GET
    @Path("/ordenadas-por-preco")
    public Response listarOrdenadasPorPreco() {
        LOG.info("🕯️ Método VelaResource.listarOrdenadasPorPreco() chamado");
        return Response.ok(velaService.listarOrdenadasPorPreco()).build();
    }


    //Atualizar vela (PUT)
    @PUT
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, VelaRequestDTO dto) {
        LOG.info(" Método VelaResource.atualizar() chamado");
        velaService.update(id, dto);
    }

    //Deletar vela(DELETE)
    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        LOG.info(" Método VelaResource.deletar() chamado");
        velaService.deletarVela(id);
    }

    @GET
    @Path("/{id}/estoque")
    @RolesAllowed({"ADMIN", "CLIENTE"})
    public Response verificarEstoque(@PathParam("id") Long id) {
    LOG.info(" Método VelaResource.verificarEstoque() chamado");
    Integer estoqueAtual = velaService.verificarEstoque(id);
    return Response.ok(Map.of(
        "velaId", id,
        "estoqueAtual", estoqueAtual,
        "mensagem", estoqueAtual > 0 ? "Disponível" : "Esgotado"
    )).build();

    }

    @GET
    @PermitAll
    public Response listarTodas() {
        List<VelaResponseDTO> velas = velaService.listarTodas();
        return Response.ok(velas).build();
    }
    @GET
    public Response findAll(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("100") int pageSize)
    {
     return Response.ok(velaService.getAll(page, pageSize)).build();
    }
    
    @GET
    @Path("/count")
    public long count(){
        return velaService.count();
    }

}

    
