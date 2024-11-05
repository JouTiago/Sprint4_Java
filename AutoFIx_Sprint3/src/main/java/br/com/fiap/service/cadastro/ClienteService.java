package br.com.fiap.service.cadastro;

import br.com.fiap.dao.cadastro.ClienteDaoInterface;
import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.factory.cadastro.ClienteFactory;

public class ClienteService {
    private final ClienteDaoInterface clienteDao;


    // Construtores

    public ClienteService(ClienteDaoInterface clienteDao) {
        this.clienteDao = clienteDao;
    }


    // Métodos

    public void cadastrarCliente(String cpf, String nome, String telefone, String endereco, String cidade,
                                 String estado, String cep) throws Exception {
        Cliente cliente = ClienteFactory.getInstance().criarCliente(cpf, nome, telefone, endereco, cidade, estado, cep);
        if (cliente.validar()) {
            clienteDao.cadastrarCliente(cliente);
        } else {
            throw new Exception("Dados de cliente inválidos.");
        }
    }

    public void alterarCliente(Cliente cliente) throws Exception {
        if (cliente.validar()) {
            clienteDao.atualizarCliente(cliente);
        } else {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
    }

    public boolean removerCliente(String cpf) {
        return clienteDao.removerClientePorCpf(cpf);
    }
}
