package br.com.fiap.model.cadastro;

import br.com.fiap.validacao.ValidadorAtributos;

public class Oficina {
    private String cnpj;
    private String nome;
    private String telefone;
    private String endereco;
    private String cidade;
    private String estado;
    private String cep;


    // Construtores
    public Oficina(String cnpj, String nome, String telefone, String endereco, String cidade, String estado, String cep) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }


    // Getters e Setters
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }


    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }


    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }


    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }


    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }



    // Métodos

    public boolean validar() throws Exception {
        ValidadorAtributos validadorAtributos = new ValidadorAtributos();
        validadorAtributos.validar(this);
        return true;
    }
}
