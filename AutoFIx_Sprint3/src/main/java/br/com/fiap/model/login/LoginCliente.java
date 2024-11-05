package br.com.fiap.model.login;

import br.com.fiap.validacao.ValidadorAtributos;

public class LoginCliente {
    private String email;
    private String senha;

    //Construtores
    public LoginCliente(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }


    // Getters e Setters

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }


    //Métodos

    //Validação simples de email e senha utilizando os validadores
    public boolean validar() throws Exception {
        ValidadorAtributos validadorAtributos = new ValidadorAtributos();
        validadorAtributos.validar(this);
        return true;
    }

}
