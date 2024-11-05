package br.com.fiap.controller.cadastro;

import br.com.fiap.model.cadastro.Oficina;
import br.com.fiap.service.cadastro.OficinaService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/oficina")
public class OficinaController {

    @Inject
    private OficinaService oficinaService;

    // Construtor padrão
    public OficinaController() {
        this.oficinaService = new OficinaService();
    }

    // Endpoint para cadastrar uma oficina
    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarOficina(Oficina oficina) {
        try {
            oficinaService.cadastrarOficina(
                    oficina.getCnpj(), oficina.getNome(), oficina.getTelefone(),
                    oficina.getEndereco(), oficina.getCidade(), oficina.getEstado(), oficina.getCep()
            );
            return Response.status(Response.Status.CREATED).entity("Oficina cadastrada com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).entity
                    ("Erro ao cadastrar oficina: " + e.getMessage()).build();
        }
    }

    // Endpoint para alterar dados da oficina
    @PUT
    @Path("/alterar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarOficina(Oficina oficina) {
        try {
            oficinaService.alterarOficina(oficina);
            return Response.ok("Dados da oficina alterados com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity
                    ("Erro ao alterar dados da oficina: " + e.getMessage()).build();
        }
    }

    // Endpoint para excluir uma oficina
    @DELETE
    @Path("/deletar/{cnpj}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirOficina(@PathParam("cnpj") String cnpj) {
        try {
            boolean sucesso = oficinaService.removerOficina(cnpj);
            if (sucesso) {
                return Response.ok("Oficina excluída com sucesso").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Oficina não encontrada").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity
                    ("Erro ao excluir oficina: " + e.getMessage()).build();
        }
    }
}
