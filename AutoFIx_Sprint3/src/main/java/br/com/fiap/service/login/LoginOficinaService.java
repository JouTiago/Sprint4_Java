package br.com.fiap.service.login;

import br.com.fiap.dao.login.LoginOficinaDaoInterface;
import br.com.fiap.model.login.LoginOficina;

public class LoginOficinaService {
    private final LoginOficinaDaoInterface loginOficinaDao;

    public LoginOficinaService(LoginOficinaDaoInterface loginOficinaDao) {
        this.loginOficinaDao = loginOficinaDao;
    }

    public void cadastrarLogin(LoginOficina loginOficina, String cnpj) throws Exception {
        if (loginOficina.validar()) {
            loginOficinaDao.cadastrarLogin(loginOficina, cnpj);
        } else {
            throw new Exception("Dados de login inválidos.");
        }
    }

    public boolean autenticarLogin(String email, String senha) {
        return loginOficinaDao.autenticarLogin(email, senha);
    }

    public void alterarLogin(LoginOficina loginOficina, String cnpj) throws Exception {
        if (loginOficina.validar()) {
            loginOficinaDao.alterarLogin(loginOficina, cnpj);
        } else {
            throw new Exception("Dados de login inválidos.");
        }
    }

    public boolean removerLogin(String email, String cnpj) {
        return loginOficinaDao.removerLogin(email, cnpj);
    }
}
