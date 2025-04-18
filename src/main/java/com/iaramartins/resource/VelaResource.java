package com.iaramartins.resource;


import java.util.List;

import com.iaramartins.dto.VelaRequestDTO;
import com.iaramartins.dto.VelaResponseDTO;
import com.iaramartins.service.VelaService;
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

 @Path("/velas")
 @Produces(MediaType.APPLICATION_JSON)
 @Consumes(MediaType.APPLICATION_JSON)

public class VelaResource {
    @Inject
    VelaService velaService;

    @POST
    @Transactional
    public Response cadastrar(VelaRequestDTO dto) {
        return Response
            .status(201)
            .entity(velaService.cadastrar(dto))
            .build();
    }

    @GET
    @Path("/disponiveis")
    public List<VelaResponseDTO> disponiveis() {
        return velaService.listarDisponiveis();
    }

    //Buscar vela pelo id (GET)
    @GET
    @Path("/{id}")
    public VelaResponseDTO buscarPorId(@PathParam("id") Long id) {
        return velaService.getById(id);
    }

    //Atualizar vela (PUT)
    @PUT
    @Path("/{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, VelaRequestDTO dto) {
        velaService.update(id, dto);
    }

    //Deletar vela(DELETE)
    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        velaService.deletarVela(id);
    }
}

    

