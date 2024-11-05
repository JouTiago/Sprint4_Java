package br.com.fiap.controller.cadastro;

import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.service.cadastro.ClienteService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/cliente")
public class ClienteController {

    @Inject
    private ClienteService clienteService;

    // Construtor padrão
    public ClienteController() {
    }


    // Endpoint para fazer o cadastro
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarCliente(Cliente cliente) {
        try {
            clienteService.cadastrarCliente(
                    cliente.getCpf(), cliente.getNome(), cliente.getTelefone(),
                    cliente.getEndereco(), cliente.getCidade(), cliente.getEstado(), cliente.getCep()
            );
            return Response.status(Response.Status.CREATED).entity("Cliente cadastrado com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity
                    ("Erro ao cadastrar cliente: " + e.getMessage()).build();
        }
    }


    // Endpoint para alterar os dados do cliente
    @PUT
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarCliente(Cliente cliente) {
        try {
            clienteService.alterarCliente(cliente);
            return Response.ok("Dados do cliente alterados com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity
                    ("Erro ao alterar dados: " + e.getMessage()).build();
        }
    }

    // Endpoint para excluir uma conta
    @DELETE
    @Path("/deletar/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirCliente(@PathParam("cpf") String cpf) {
        try {
            boolean sucesso = clienteService.removerCliente(cpf);
            if (sucesso) {
                return Response.ok("Cliente excluído com sucesso").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity
                    ("Erro ao excluir cliente: " + e.getMessage()).build();
        }
    }
}
