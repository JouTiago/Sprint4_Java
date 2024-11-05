package br.com.fiap.model.cadastro;

import br.com.fiap.validacao.ValidadorAtributos;

public class Veiculo {
    private String placa;
    private String marca;
    private String modelo;
    private int ano;
    private String combustivel;
    private String transmissao;
    private int quilometragem;
    private String modificacoes;
    private Cliente cliente;


    // Construtores
    public Veiculo() {}

    public Veiculo(String placa, String marca, String modelo, int ano, String combustivel, String transmissao,
                   int quilometragem, String modificacoes, Cliente cliente) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.combustivel = combustivel;
        this.transmissao = transmissao;
        this.quilometragem = quilometragem;
        this.modificacoes = modificacoes;
        this.cliente = cliente;
    }


    // Getters e Setters
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }


    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }


    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }


    public String getCombustivel() {
        return combustivel;
    }
    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }


    public String getTransmissao() {
        return transmissao;
    }
    public void setTransmissao(String transmissao) {
        this.transmissao = transmissao;
    }


    public int getQuilometragem() {
        return quilometragem;
    }
    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }


    public String getModificacoes() {
        return modificacoes;
    }
    public void setModificacoes(String modificacoes) {
        this.modificacoes = modificacoes;
    }


    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


    // Métodos

    public boolean validar() throws Exception {
        ValidadorAtributos validadorAtributos = new ValidadorAtributos();
        validadorAtributos.validar(this);
        return true;
    }
}
