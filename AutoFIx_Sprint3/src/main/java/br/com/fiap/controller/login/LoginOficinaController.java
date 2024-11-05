package br.com.fiap.controller.login;

import br.com.fiap.model.login.LoginOficina;
import br.com.fiap.service.login.LoginOficinaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login/oficina")
public class LoginOficinaController {

    @Inject
    private LoginOficinaService loginOficinaService;

    public LoginOficinaController() {}

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarLogin(@FormParam("cnpj") String cnpj, LoginOficina loginOficina) {
        try {
            loginOficinaService.cadastrarLogin(loginOficina, cnpj);
            return Response.status(Response.Status.CREATED).entity("Login cadastrado com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity("Erro ao cadastrar login: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/autenticar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autenticarLogin(@FormParam("email") String email, @FormParam("senha") String senha) {
        try {
            boolean autenticado = loginOficinaService.autenticarLogin(email, senha);
            if (autenticado) {
                return Response.ok("Login realizado com sucesso").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao realizar login: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarLogin(@FormParam("cnpj") String cnpj, LoginOficina loginOficina) {
        try {
            loginOficinaService.alterarLogin(loginOficina, cnpj);
            return Response.ok("Dados do login alterados com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Erro ao alterar login: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deletar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerLogin(@FormParam("email") String email, @FormParam("cnpj") String cnpj) {
        try {
            boolean sucesso = loginOficinaService.removerLogin(email, cnpj);
            if (sucesso) {
                return Response.ok("Login removido com sucesso").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Login não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover login: " + e.getMessage()).build();
        }
    }
}
