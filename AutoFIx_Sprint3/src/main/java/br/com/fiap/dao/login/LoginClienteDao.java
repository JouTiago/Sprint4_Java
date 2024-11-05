package br.com.fiap.dao.login;

import br.com.fiap.dao.cadastro.ClienteDaoInterface;
import br.com.fiap.factory.login.ClienteDaoFactory;
import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.model.login.LoginCliente;
import br.com.fiap.util.OperacoesSql;

public class LoginClienteDao implements LoginClienteDaoInterface {
    private static final String TABELA = "T_LOGIN_CLIENTE";
    private static final String[] COLUNAS = {"id_login_cliente", "login_cliente", "senha_cliente", "c_cpf"};
    private final ClienteDaoInterface clienteDao;


    // Construtores


    // Construtor que recebe a dependência da ClienteDaoInterface
    public LoginClienteDao(ClienteDaoInterface clienteDao) {
        this.clienteDao = clienteDao;
    }


    // Métodos

    @Override
    public void cadastrarLogin(LoginCliente loginCliente, String cpf) {
        ClienteDaoInterface clienteDao = ClienteDaoFactory.getInstance();
        Cliente cliente = clienteDao.buscarPorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF fornecido.");
        }
        if (OperacoesSql.loginExiste(TABELA, loginCliente.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {null, loginCliente.getEmail(), loginCliente.getSenha(), cpf};
        OperacoesSql.inserirNoBanco(TABELA, valores, sqlInsert);
    }

    @Override
    public boolean autenticarLogin(String email, String senha) {
        if (OperacoesSql.verificaLogin(email, senha)) {
            return true;
        } else {
            throw new IllegalArgumentException("Falha ao realizar login.");
        }
    }

    @Override
    public void alterarLogin(LoginCliente loginCliente, String cpf) {
        Cliente cliente = clienteDao.buscarPorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF fornecido.");
        }

        String[] colunasAtualizar = {"login_cliente", "senha_cliente"};
        Object[] valoresAtualizar = {loginCliente.getEmail(), loginCliente.getSenha()};
        String condicao = "c_cpf = ?";
        Object[] parametrosCondicao = {cpf};
        OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }

    @Override
    public boolean removerLogin(String email, String cpf) {
        Cliente cliente = clienteDao.buscarPorCpf(cpf);

        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para o CPF fornecido.");
        }

        String condicao = "login_cliente = ? AND c_cpf = ?";
        Object[] parametrosCondicao = {email, cpf};
        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
