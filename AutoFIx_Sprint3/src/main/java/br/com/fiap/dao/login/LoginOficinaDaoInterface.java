package br.com.fiap.dao.login;

import br.com.fiap.model.login.LoginOficina;

public interface LoginOficinaDaoInterface {
    void cadastrarLogin(LoginOficina loginOficina, String cnpj);
    boolean autenticarLogin(String email, String senha);
    void alterarLogin(LoginOficina loginOficina, String cnpj);
    boolean removerLogin(String email, String cnpj);
}
