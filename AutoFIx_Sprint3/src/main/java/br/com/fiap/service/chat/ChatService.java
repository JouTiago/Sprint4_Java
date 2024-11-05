package br.com.fiap.service.chat;

import br.com.fiap.model.cadastro.Veiculo;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ChatService {
    String iniciarSessaoChat(String token, Veiculo veiculo, String cpf);
    String enviarMensagem(String chatId, String mensagem, String token);
    boolean encerrarSessaoChat(String chatId, String token);
}
