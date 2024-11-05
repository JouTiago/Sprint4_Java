package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.util.OperacoesSql;

public class ClienteDao implements ClienteDaoInterface {
    private static final String TABELA = "T_CLIENTE";
    private static final String[] COLUNAS = {"c_cpf", "c_nome", "c_telefone", "c_endereco",
            "c_cidade", "c_estado", "c_cep"};


    //Métodos

    //Cadastrar novo cliente
    @Override
    public void cadastrarCliente(Cliente cliente) {
        if (OperacoesSql.clienteExiste(TABELA, cliente.getCpf())) {
            throw new IllegalArgumentException("Cliente já cadastrado com esse CPF.");
        }

        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {cliente.getCpf(), cliente.getNome(), cliente.getTelefone(), cliente.getEndereco(),
                cliente.getCidade(), cliente.getEstado(), cliente.getCep()};

        OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    //Atualizar os dados do cliente no banco
    @Override
    public void atualizarCliente(Cliente cliente) {
        String[] colunasAtualizar = {"c_telefone", "c_endereco", "c_cidade", "c_estado", "c_cep"};
        Object[] valoresAtualizar = {cliente.getTelefone(), cliente.getEndereco(), cliente.getCidade(),
                cliente.getEstado(), cliente.getCep()};

        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cliente.getCpf()};

        OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }

    //Buscar um cliente pelo CPF
    @Override
    public Cliente buscarPorCpf(String cpf) {
        return OperacoesSql.buscarClientePorCpf(cpf);
    }


    //Remover um cliente pelo CPF
    @Override
    public boolean removerClientePorCpf(String cpf) {
        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cpf};

        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
