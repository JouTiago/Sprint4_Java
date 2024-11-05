package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Cliente;

public interface ClienteDaoInterface {


    //Métodos

    void cadastrarCliente(Cliente cliente);
    void atualizarCliente(Cliente cliente);
    Cliente buscarPorCpf(String cpf);
    boolean removerClientePorCpf(String cpf);
}
