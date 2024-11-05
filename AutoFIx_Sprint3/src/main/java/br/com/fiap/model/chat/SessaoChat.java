package br.com.fiap.model.chat;

import br.com.fiap.model.cadastro.Cliente;

public class SessaoChat {
    private final String chatId;
    private final Cliente cliente;
    private boolean ativo;

    public SessaoChat(String chatId, Cliente cliente) {
        this.chatId = chatId;
        this.cliente = cliente;
        this.ativo = true;
    }

    public String getChatId() {
        return chatId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void encerrarSessao() {
        this.ativo = false;
    }
}
