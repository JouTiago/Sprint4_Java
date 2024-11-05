package br.com.fiap.service.login;

import br.com.fiap.dao.login.LoginClienteDaoInterface;
import br.com.fiap.model.login.LoginCliente;

public class LoginClienteService {
    private final LoginClienteDaoInterface loginClienteDao;

    // Construtores

    public LoginClienteService(LoginClienteDaoInterface loginClienteDao) {
        this.loginClienteDao = loginClienteDao;
    }


    // Métodos

    public void cadastrarLogin(LoginCliente loginCliente, String cpf) throws Exception {
        if (loginCliente.validar()) {
            loginClienteDao.cadastrarLogin(loginCliente, cpf);
        } else {
            throw new Exception("Dados de login inválidos.");
        }
    }

    public boolean autenticarLogin(String email, String senha) {
        return loginClienteDao.autenticarLogin(email, senha);
    }

    public void alterarLogin(LoginCliente loginCliente, String cpf) throws Exception {
        if (loginCliente.validar()) {
            loginClienteDao.alterarLogin(loginCliente, cpf);
        } else {
            throw new Exception("Dados de login inválidos.");
        }
    }

    public boolean removerLogin(String email, String cpf) {
        return loginClienteDao.removerLogin(email, cpf);
    }
}

