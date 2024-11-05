package br.com.fiap.dao.login;

import br.com.fiap.model.login.LoginCliente;

public interface LoginClienteDaoInterface {


    // Métodos

    void cadastrarLogin(LoginCliente loginCliente, String cpf);
    boolean autenticarLogin(String email, String senha);
    void alterarLogin(LoginCliente loginCliente, String cpf);
    boolean removerLogin(String email, String cpf);
}
