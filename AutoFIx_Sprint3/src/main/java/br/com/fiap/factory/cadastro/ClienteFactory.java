package br.com.fiap.factory.cadastro;

import br.com.fiap.model.cadastro.Cliente;

public class ClienteFactory {
    private static ClienteFactory instance;


    // Construtores

    private ClienteFactory() {}

    public synchronized static ClienteFactory getInstance() {
        if (instance == null) {
            instance = new ClienteFactory();
        }
        return instance;
    }


    // Métodos

    // Criar um novo cliente
    public Cliente criarCliente(String cpf, String nome, String telefone, String endereco, String cidade, String estado, String cep) {
        return new Cliente(cpf, nome, telefone, endereco, cidade, estado, cep);
    }
}
