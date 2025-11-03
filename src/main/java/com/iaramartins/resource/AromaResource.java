package com.iaramartins.resource;


import java.util.List;

import com.iaramartins.model.Aroma;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/aromas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AromaResource {
    @GET
    public List<Aroma>listaAroma(){
        return Aroma.listAll();
    }

    @GET
    @Path("/{id}")
    public Aroma buscarPorId(@PathParam("id") Long id) {
        return Aroma.findById(id);
    }

    @POST
    @Transactional
    public Aroma adicionar(Aroma aroma){
        aroma.persist();
        return aroma;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Aroma atualizar(@PathParam("id") Long id, Aroma aroma) {
        Aroma existente = Aroma.findById(id);
        if (existente == null) {
            throw new NotFoundException();
        }
        existente.setEssenciaAromatica(aroma.getEssenciaAromatica());
        return existente;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id) {
        Aroma.deleteById(id);
    }

    
    
}
