package br.com.fiap.service.chat;

import br.com.fiap.model.cadastro.Cliente;
import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.model.chat.SessaoChat;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Singleton;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Service
@Singleton
public class ChatServiceImpl implements ChatService {
    private static final String PYTHON_CHAT_URL = "http://192.168.15.15:5001/chat";
    private static final String SERVICE_TOKEN = "teste-token";
    private final Client client;
    private final Map<String, SessaoChat> sessoesAtivas;

    public ChatServiceImpl() {
        this.client = ClientBuilder.newClient();
        this.sessoesAtivas = new HashMap<>();
    }

    @Override
    public String iniciarSessaoChat(String token, Veiculo veiculo, String cpf) {
        String url = PYTHON_CHAT_URL + "/init";

        Map<String, Object> payload = new HashMap<>();
        payload.put("cpf", cpf);

        Map<String, String> veiculoInfo = new HashMap<>();
        veiculoInfo.put("marca", veiculo.getMarca());
        veiculoInfo.put("modelo", veiculo.getModelo());
        veiculoInfo.put("ano", String.valueOf(veiculo.getAno()));
        payload.put("veiculo", veiculoInfo);

        try (Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Service-Token", SERVICE_TOKEN)
                .header("Cpf", cpf)
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON))) {

            if (response.getStatus() == 200) {
                Map<String, Object> resp = response.readEntity(Map.class);
                String chatId = (String) resp.get("chat_id");

                // Armazena a sessão de chat ativa
                Cliente cliente = new Cliente(cpf, null, null, null, null, null, null);
                sessoesAtivas.put(chatId, new SessaoChat(chatId, cliente));
                return chatId;
            } else {
                System.err.println("Erro ao iniciar sessão de chat: " + response.readEntity(String.class));
                return null;
            }
        }
    }

    @Override
    public String enviarMensagem(String chatId, String mensagem, String token) {
        String url = PYTHON_CHAT_URL + "/send";

        Map<String, String> payload = new HashMap<>();
        payload.put("chatId", chatId);
        payload.put("mensagem", mensagem);
        payload.put("Service-Token", token);

        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Service-Token", SERVICE_TOKEN)
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            Map<String, Object> resp = response.readEntity(Map.class);
            return (String) resp.get("response");
        } else {
            String errorResponse = response.readEntity(String.class);
            System.err.println("Erro ao enviar mensagem. Status: " + response.getStatus() + ", Resposta: " + errorResponse);
            return null;
        }
    }

    @Override
    public boolean encerrarSessaoChat(String chatId, String token) {
        String url = PYTHON_CHAT_URL + "/end";

        Map<String, String> payload = new HashMap<>();
        payload.put("chat_id", chatId);
        payload.put("token", token);

        Response response = client.target(url)
                .request(MediaType.APPLICATION_JSON)
                .header("Service-Token", SERVICE_TOKEN)
                .post(Entity.entity(payload, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            // Remove a sessão ativa e marca como inativa
            SessaoChat sessaoChat = sessoesAtivas.remove(chatId);
            if (sessaoChat != null) {
                sessaoChat.encerrarSessao();
            }
            return true;
        } else {
            System.err.println("Erro ao encerrar sessão de chat: " + response.readEntity(String.class));
            return false;
        }
    }
}
