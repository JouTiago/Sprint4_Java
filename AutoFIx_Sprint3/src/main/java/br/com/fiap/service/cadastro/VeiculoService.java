package br.com.fiap.service.cadastro;

import br.com.fiap.dao.cadastro.VeiculoDaoInterface;
import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.model.cadastro.Cliente;

public class VeiculoService {
    private final VeiculoDaoInterface veiculoDao;


    // Construtores
    public VeiculoService(VeiculoDaoInterface veiculoDao) {
        this.veiculoDao = veiculoDao;
    }


    // Métodos
    public void cadastrarVeiculo(String placa, String marca, String modelo, int ano, String combustivel,
                                 String transmissao, int quilometragem, String modificacoes, Cliente cliente) throws Exception {
        Veiculo veiculo = new Veiculo(placa, marca, modelo, ano, combustivel, transmissao, quilometragem, modificacoes, cliente);
        if (veiculo.validar()) {
            veiculoDao.cadastrarVeiculo(veiculo);
        } else {
            throw new Exception("Dados do veículo inválidos.");
        }
    }


    public Veiculo buscarVeiculo(String placa) {
        return veiculoDao.buscarPorPlaca(placa);
    }


    public void alterarVeiculo(Veiculo veiculo) throws Exception {
        if (veiculo.validar()) {
            veiculoDao.atualizarVeiculo(veiculo);
        } else {
            throw new IllegalArgumentException("Veículo não encontrado.");
        }
    }


    public boolean removerVeiculo(String placa) {
        return veiculoDao.removerVeiculo(placa);
    }

}
