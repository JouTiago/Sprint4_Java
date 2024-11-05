package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Oficina;
import br.com.fiap.util.OperacoesSql;

public class OficinaDao implements OficinaDaoInterface {
    private static final String TABELA = "T_OFICINA";
    private static final String[] COLUNAS = {"o_cnpj", "o_nome", "o_telefone", "o_endereco",
            "o_cidade", "o_estado", "o_cep"};


    // Métodos

    @Override
    public void cadastrarOficina(Oficina oficina) {
        if (OperacoesSql.oficinaExiste(TABELA, oficina.getCnpj())) {
            throw new IllegalArgumentException("Oficina já cadastrada com esse CNPJ.");
        }

        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {oficina.getCnpj(), oficina.getNome(), oficina.getTelefone(), oficina.getEndereco(),
                oficina.getCidade(), oficina.getEstado(), oficina.getCep()};
        OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    @Override
    public Oficina buscarPorCnpj(String cnpj) {
        return OperacoesSql.buscarOficinaPorCnpj(cnpj);
    }

    @Override
    public void atualizarOficina(Oficina oficina) {
        String[] colunasAtualizar = {"o_nome", "o_telefone", "o_endereco", "o_cidade", "o_estado", "o_cep"};
        Object[] valoresAtualizar = {oficina.getNome(), oficina.getTelefone(), oficina.getEndereco(),
                oficina.getCidade(), oficina.getEstado(), oficina.getCep()};

        String condicao = "o_cnpj = ?";
        Object[] parametrosCondicao = {oficina.getCnpj()};

        OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }

    @Override
    public boolean removerOficina(String cnpj) {
        String condicao = "o_cnpj = ?";
        Object[] parametrosCondicao = {cnpj};
        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
