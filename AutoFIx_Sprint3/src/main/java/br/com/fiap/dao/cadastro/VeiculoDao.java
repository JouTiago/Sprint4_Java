package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.util.OperacoesSql;

public class VeiculoDao implements VeiculoDaoInterface {
    private static final String TABELA = "T_VEICULO";
    private static final String[] COLUNAS = {"v_placa", "v_marca", "v_modelo", "v_ano", "v_combustivel",
            "v_transmissao", "v_km", "v_modificacoes", "c_cpf"};

    @Override
    public void cadastrarVeiculo(Veiculo veiculo) {
        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {veiculo.getPlaca(), veiculo.getMarca(), veiculo.getModelo(), veiculo.getAno(),
                veiculo.getCombustivel(), veiculo.getTransmissao(), veiculo.getQuilometragem(),
                veiculo.getModificacoes(), veiculo.getCliente().getCpf()};
        OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        return OperacoesSql.buscarVeiculoPorPlaca(placa);
    }

    @Override
    public void atualizarVeiculo(Veiculo veiculo) {
        String[] colunasAtualizar = {"v_combustivel", "v_transmissao", "v_km", "v_modificacoes"};
        Object[] valoresAtualizar = {veiculo.getCombustivel(), veiculo.getTransmissao(), veiculo.getQuilometragem(),
                veiculo.getModificacoes()};
        String condicao = "v_placa = ?";
        Object[] parametrosCondicao = {veiculo.getPlaca()};
        OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }

    @Override
    public boolean removerVeiculo(String placa) {
        String condicao = "v_placa = ?";
        Object[] parametrosCondicao = {placa};
        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
