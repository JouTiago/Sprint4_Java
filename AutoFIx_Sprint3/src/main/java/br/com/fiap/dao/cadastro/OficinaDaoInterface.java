package br.com.fiap.dao.cadastro;

import br.com.fiap.model.cadastro.Oficina;

public interface OficinaDaoInterface {
    void cadastrarOficina(Oficina oficina);
    Oficina buscarPorCnpj(String cnpj);
    void atualizarOficina(Oficina oficina);
    boolean removerOficina(String cnpj);
}
