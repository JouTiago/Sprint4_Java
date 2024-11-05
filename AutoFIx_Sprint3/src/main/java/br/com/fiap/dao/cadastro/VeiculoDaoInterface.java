package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Veiculo;

public interface VeiculoDaoInterface {
    void cadastrarVeiculo(Veiculo veiculo);
    Veiculo buscarPorPlaca(String placa);
    void atualizarVeiculo(Veiculo veiculo);
    boolean removerVeiculo(String placa);
}

