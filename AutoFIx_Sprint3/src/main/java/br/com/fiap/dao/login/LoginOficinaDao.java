package br.com.fiap.dao.login;

import br.com.fiap.dao.cadastro.OficinaDaoInterface;
import br.com.fiap.factory.login.OficinaDaoFactory;
import br.com.fiap.model.login.LoginOficina;
import br.com.fiap.model.cadastro.Oficina;
import br.com.fiap.util.OperacoesSql;

public class LoginOficinaDao implements LoginOficinaDaoInterface {
    private static final String TABELA = "T_LOGIN_OFICINA";
    private static final String[] COLUNAS = {"id_login_oficina", "login_oficina", "senha_oficina", "o_cnpj"};
    private final OficinaDaoInterface oficinaDao;

    public LoginOficinaDao(OficinaDaoInterface oficinaDao) {
        this.oficinaDao = oficinaDao;
    }

    @Override
    public void cadastrarLogin(LoginOficina loginOficina, String cnpj) {
        OficinaDaoInterface oficinaDao = OficinaDaoFactory.getInstance();
        Oficina oficina = oficinaDao.buscarPorCnpj(cnpj);

        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não encontrada para o CNPJ fornecido.");
        }
        if (OperacoesSql.loginExiste(TABELA, loginOficina.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

        String sqlInsert = OperacoesSql.gerarInsertTabela(TABELA, COLUNAS);
        Object[] valores = {null, loginOficina.getEmail(), loginOficina.getSenha(), cnpj};
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
    public void alterarLogin(LoginOficina loginOficina, String cnpj) {
        Oficina oficina = oficinaDao.buscarPorCnpj(cnpj);

        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não encontrada para o CNPJ fornecido.");
        }

        String[] colunasAtualizar = {"login_oficina", "senha_oficina"};
        Object[] valoresAtualizar = {loginOficina.getEmail(), loginOficina.getSenha()};
        String condicao = "o_cnpj = ?";
        Object[] parametrosCondicao = {cnpj};
        OperacoesSql.executarUpdate(TABELA, colunasAtualizar, valoresAtualizar, condicao, parametrosCondicao);
    }

    @Override
    public boolean removerLogin(String email, String cnpj) {
        Oficina oficina = oficinaDao.buscarPorCnpj(cnpj);

        if (oficina == null) {
            throw new IllegalArgumentException("Oficina não encontrada para o CNPJ fornecido.");
        }

        String condicao = "login_oficina = ? AND o_cnpj = ?";
        Object[] parametrosCondicao = {email, cnpj};
        return OperacoesSql.executarDelete(TABELA, condicao, parametrosCondicao);
    }
}
