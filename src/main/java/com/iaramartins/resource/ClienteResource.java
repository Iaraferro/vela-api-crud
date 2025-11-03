package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.net.URI;

import com.iaramartins.dto.ClienteRequestDTO;
import com.iaramartins.dto.ClienteResponseDTO;
import com.iaramartins.service.ClienteService;


import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import jakarta.ws.rs.core.Response.Status;

//@Authenticated
@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
  private static final Logger LOG = Logger.getLogger(ClienteResource.class);

    @Inject
    ClienteService clienteService;
    
   // Cadastrar Cliente
   @POST // Diz que esse método responde a uma requisição POST
   @Transactional
   @PermitAll
   public Response cadastrar( @Valid ClienteRequestDTO dto){
    LOG.info(" Método ClienteResource.cadastrar() chamado");
     try {
        ClienteResponseDTO response = clienteService.cadastrar(dto);
        return Response
            .created(URI.create("/clientes/" + response.id()))
            .entity(response)
            .build();
        } catch (Exception e) {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity("Erro ao cadastrar: " + e.getMessage())
            .build();
        }
   }

   //Buscar o próprio perfil (só o cliente logado)
   @GET
   @Path("/{id}")
   //@RolesAllowed("CLIENTE") //Exige autenticação
   public Response buscarPerfil(@PathParam("id") Long id){
    LOG.info("🙋 Método ClienteResource.buscarPerfil() chamado");
    try {
        return Response.ok(clienteService.buscarPerfil(id)).build();
        } catch (NotFoundException e) {
        return Response.status(Status.NOT_FOUND).build();
        }
   }
   @GET
   public Response fidAll(
    @QueryParam("page") @DefaultValue("0") int page,
    @QueryParam("pageSize") @DefaultValue("100") int pageSize){
           return Response.ok(clienteService.getAll(page, pageSize)).build();
   }

   @GET
   @Path("/count")
   public long count(){
    return clienteService.count();
   }

   //Atualizar os próprios dados
   @PUT
   @Path("/{id}")
   @Transactional
   //@RolesAllowed("CLIENTE") //Exige autenticação
   public Response atualizar(@PathParam("id") Long id, ClienteRequestDTO dto){
        LOG.info(" Método ClienteResource.atualizar() chamado");
        try {
        clienteService.atualizar(id, dto);
        return Response.noContent().build();
        } catch (NotFoundException e) {
        return Response.status(Status.NOT_FOUND).build();
        }
   }

   //Deletar conta (só o cliente logado)
   @DELETE
   @Path("/{id}")
   @Transactional
   //@RolesAllowed("CLIENTE") //Exige autenticação
   public void deletar(@PathParam("id") Long id){
     LOG.info(" Método ClienteResource.deletar() chamado");
     clienteService.deletarCliente(id);
   }

   @GET
   //@RolesAllowed("ADMIN") // Somente administradores podem listar todos clientes
   @PermitAll
   public Response listarTodos() {
    LOG.info(" Método ClienteResource.listarTodos() chamado");
    return Response.ok(clienteService.listarTodos()).build();
  }

   @GET
   @Path("/buscar")
  // @RolesAllowed("ADMIN") // Somente administradores podem buscar clientes
   public Response buscarPorNome(@QueryParam("nome") String nome) {
    LOG.info(" Método ClienteResource.buscarPorNome() chamado");
    if (nome == null || nome.isBlank()) {
        return Response.status(Response.Status.BAD_REQUEST)
            .entity("O parâmetro 'nome' é obrigatório")
            .build();
    }
    return Response.ok(clienteService.buscarPorNome(nome)).build();
  }
}   
