package com.iaramartins.resource;
import org.jboss.logging.Logger;

import java.util.Set;

import com.iaramartins.dto.LoginRequestDTO;
import com.iaramartins.dto.LoginResponseDTO;
import com.iaramartins.model.Usuario;
import com.iaramartins.repository.UsuarioRepository;
import com.iaramartins.service.JwtService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private static final Logger LOG = Logger.getLogger(AuthResource.class);
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JwtService jwtService;

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO dto){
        LOG.info(" Método AuthResource.login() chamado");
        Usuario u = usuarioRepository.findByEmail(dto.email());

        if (u == null || !u.getSenha().equals(dto.senha())) {
            LOG.info(" Método AuthResource.login() - credenciais inválidas");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

       String token = jwtService.generateToken(
       u.getEmail(),
       Set.of(u.getRole().name())
       );

        return Response.ok(new LoginResponseDTO(token)).build();
    }
}
