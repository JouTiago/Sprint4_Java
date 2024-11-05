package br.com.fiap.factory.login;

import br.com.fiap.dao.cadastro.OficinaDao;
import br.com.fiap.dao.cadastro.OficinaDaoInterface;


public class OficinaDaoFactory {
    private static OficinaDaoInterface instance;


    private OficinaDaoFactory() {}


    public static OficinaDaoInterface getInstance() {
        if (instance == null) {
            instance = new OficinaDao();
        }
        return instance;
    }

}
