package br.com.fiap.factory.login;

import br.com.fiap.dao.cadastro.ClienteDao;
import br.com.fiap.dao.cadastro.ClienteDaoInterface;

public class ClienteDaoFactory {
    private static ClienteDaoInterface instancia;

    private ClienteDaoFactory() {
    }

    public static ClienteDaoInterface getInstance() {
        if (instancia == null) {
            instancia = new ClienteDao();
        }
        return instancia;
    }
}
