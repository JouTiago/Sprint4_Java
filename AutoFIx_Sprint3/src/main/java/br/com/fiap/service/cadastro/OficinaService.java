package br.com.fiap.service.cadastro;

import br.com.fiap.dao.cadastro.OficinaDaoInterface;
import br.com.fiap.factory.login.OficinaDaoFactory;
import br.com.fiap.model.cadastro.Oficina;

public class OficinaService {
    private final OficinaDaoInterface oficinaDao;


    // Construtores
    public OficinaService() {
        this.oficinaDao = OficinaDaoFactory.getInstance();
    }


    // Métodos


    public void cadastrarOficina(String cnpj, String nome, String telefone, String endereco,
                                 String cidade, String estado, String cep) throws Exception {
        Oficina oficina = new Oficina(cnpj, nome, telefone, endereco, cidade, estado, cep);
        if (oficina.validar()) {
            oficinaDao.cadastrarOficina(oficina);
        } else {
            throw new Exception("Dados de oficina inválidos.");
        }
    }

    public Oficina buscarOficina(String cnpj) {
        return oficinaDao.buscarPorCnpj(cnpj);
    }

    public void alterarOficina(Oficina oficina) throws Exception {
        if (oficina.validar()) {
            oficinaDao.atualizarOficina(oficina);
        } else {
            throw new IllegalArgumentException("Oficina não encontrada.");
        }
    }

    public boolean removerOficina(String cnpj) {
        return oficinaDao.removerOficina(cnpj);
    }
}
