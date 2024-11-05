package br.com.fiap.controller.login;

import br.com.fiap.model.login.LoginCliente;
import br.com.fiap.service.login.LoginClienteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login/cliente")
public class LoginClienteController {

    @Inject
    private LoginClienteService loginClienteService;


    // Construtores
    public LoginClienteController() {}


    // Endpoint para fazer o cadastro do login
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarLogin(@FormParam("cpf") String cpf, LoginCliente loginCliente) {
        try {
            loginClienteService.cadastrarLogin(loginCliente, cpf);
            return Response.status(Response.Status.CREATED).entity("Login cadastrado com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity
                    ("Erro ao cadastrar login: " + e.getMessage()).build();
        }
    }

    // Endpoint para autenticar um login
    @POST
    @Path("/autenticar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autenticarLogin(@FormParam("email") String email, @FormParam("senha") String senha) {
        try {
            boolean autenticado = loginClienteService.autenticarLogin(email, senha);
            if (autenticado) {
                return Response.ok("Login realizado com sucesso").build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Credenciais inválidas").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity
                    ("Erro ao realizar login: " + e.getMessage()).build();
        }
    }

    // Endpoint para alterar o login (email e/ou senha)
    @PUT
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarLogin(@FormParam("cpf") String cpf, LoginCliente loginCliente) {
        try {
            loginClienteService.alterarLogin(loginCliente, cpf);
            return Response.ok("Dados do login alterados com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity
                    ("Erro ao alterar login: " + e.getMessage()).build();
        }
    }

    // Endpoint para remover o login
    @DELETE
    @Path("/deletar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerLogin(@FormParam("email") String email, @FormParam("cpf") String cpf) {
        try {
            boolean sucesso = loginClienteService.removerLogin(email, cpf);
            if (sucesso) {
                return Response.ok("Login removido com sucesso").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Login não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity
                    ("Erro ao remover login: " + e.getMessage()).build();
        }
    }
}
