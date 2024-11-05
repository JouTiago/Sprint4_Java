package br.com.fiap.factory.cadastro;

import br.com.fiap.model.cadastro.Oficina;

public class OficinaFactory {
    private static OficinaFactory instance;


    // Construtores
    private OficinaFactory() {}


    // Instância única da oficina
    public static OficinaFactory getInstance() {
        if (instance == null) {
            instance = new OficinaFactory();
        }
        return instance;
    }


    // Criar uma nova oficina
    public Oficina criarOficina(String cnpj, String nome, String telefone, String endereco, String cidade, String estado, String cep) {
        return new Oficina(cnpj, nome, telefone, endereco, cidade, estado, cep);
    }
}
