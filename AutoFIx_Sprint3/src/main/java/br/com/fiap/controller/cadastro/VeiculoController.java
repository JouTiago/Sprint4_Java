package br.com.fiap.controller.cadastro;

import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.service.cadastro.VeiculoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("area-cliente/garagem")
public class VeiculoController {
    @Inject
    private VeiculoService veiculoService;


    // Construtores
    public VeiculoController() {}


    // Endpoints

    // Endpoint para cadastrar veículo
    @POST
    @Path("/cadastrar-veiculo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cadastrarVeiculo(Veiculo veiculo) {
        try {
            veiculoService.cadastrarVeiculo(
                    veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(),
                    veiculo.getAno(), veiculo.getCombustivel(), veiculo.getTransmissao(),
                    veiculo.getQuilometragem(), veiculo.getModificacoes(), veiculo.getCliente()
            );
            return Response.status(Response.Status.CREATED).entity("Veículo cadastrado com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.CONFLICT).
                    entity("Erro ao cadastrar veículo: " + e.getMessage()).build();
        }
    }

    // Endpoint para buscar veículo
    @GET
    @Path("/buscar-veiculo/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarVeiculo(@PathParam("placa") String placa) {
        try {
            Veiculo veiculo = veiculoService.buscarVeiculo(placa);
            if (veiculo != null) {
                return Response.ok(veiculo).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Veículo não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Erro ao buscar veículo: " + e.getMessage()).build();
        }
    }

    // Endpoint para alterar veículo
    @PUT
    @Path("/alterar-veiculo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarVeiculo(Veiculo veiculo) {
        try {
            veiculoService.alterarVeiculo(veiculo);
            return Response.ok("Dados do veículo alterados com sucesso").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).
                    entity("Erro ao alterar veículo: " + e.getMessage()).build();
        }
    }

    // Endpoint para remover veículo
    @DELETE
    @Path("/deletar-veiculo/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removerVeiculo(@PathParam("placa") String placa) {
        try {
            boolean sucesso = veiculoService.removerVeiculo(placa);
            if (sucesso) {
                return Response.ok("Veículo removido com sucesso").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Veículo não encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity("Erro ao remover veículo: " + e.getMessage()).build();
        }
    }
}