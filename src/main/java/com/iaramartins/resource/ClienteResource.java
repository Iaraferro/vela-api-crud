package com.iaramartins.resource;

import com.iaramartins.dto.ClienteRequestDTO;
import com.iaramartins.dto.ClienteResponseDTO;
import com.iaramartins.service.ClienteService;

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

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
    @Inject
    ClienteService clienteService;
    
   // Cadastrar Cliente
   @POST // Diz que esse método responde a uma requisição POST
   @Transactional
   public Response cadastrar(ClienteRequestDTO dto){
   ClienteResponseDTO cliente = clienteService.cadastrar(dto);
   return Response.status(Response.Status.CREATED).entity(cliente).build(); //Mostra como será retornado para o cliente
   }

   //Buscar o próprio perfil (só o cliente logado)
   @GET
   @Path("/{id}")
   @RolesAllowed("cliente") //Exige autenticação
   public ClienteResponseDTO buscarPerfil(@PathParam("id") Long id){
    return clienteService.buscarPerfil(id);
   }

   //Atualizar os próprios dados
   @PUT
   @Path("/{id}")
   @Transactional
   @RolesAllowed("cliente") //Exige autenticação
   public void atualizar(@PathParam("id") Long id, ClienteRequestDTO dto){
       clienteService.atualizar(id, dto);
   }

   //Deletar conta (só o cliente logado)
   @DELETE
   @Path("/{id}")
   @Transactional
   @RolesAllowed("cliente") //Exige autenticação
   public void deletar(@PathParam("id") Long id){
     clienteService.deletarCliente(id);
   }
}   
