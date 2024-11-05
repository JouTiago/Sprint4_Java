package br.com.fiap.controller.chat;

import br.com.fiap.model.cadastro.Veiculo;
import br.com.fiap.service.chat.ChatService;
import br.com.fiap.validacao.ValidarToken;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import javax.ws.rs.core.HttpHeaders;

@Path("/chat")
public class ChatController {

    @Inject
    private ChatService chatService;

    private static final String INITIAL_MESSAGE = "Olá, sou o chatbot da Autofix e vou te " +
            "ajudar a identificar quaisquer problemas que "
            + "você possa estar tendo com o seu veículo. Qual problema você está enfrentando?";

    public ChatController() {
        System.out.println("ChatController: instância criada");
    }

    @POST
    @Path("/init")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarChat(Veiculo veiculo, @Context HttpHeaders headers) {
        System.out.println("Recebi uma requisição POST em /chat/init");

        if (chatService == null) {
            System.out.println("Erro: ChatService não foi injetado");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Serviço de chat não disponível.\"}")
                    .build();
        }

        try {
            if (veiculo == null) {
                System.out.println("Veiculo é null");
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"Dados do veículo não fornecidos ou inválidos.\"}")
                        .build();
            }

            String token = headers.getHeaderString("Service-Token");
            if (token == null || token.isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Token não fornecido.\"}")
                        .build();
            }

            String cpf = headers.getHeaderString("Cpf");
            if (cpf == null || cpf.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"error\":\"CPF não fornecido.\"}")
                        .build();
            }

            String chatId = chatService.iniciarSessaoChat(token, veiculo, cpf);

            if (chatId != null) {
                return Response.ok("{\"chat_id\":\"" + chatId + "\", \"initial_message\":\"" + INITIAL_MESSAGE + "\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"Não foi possível iniciar a sessão de chat.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }


    // Endpoint para enviar uma mensagem no chat
    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enviarMensagem(MensagemChat mensagemChat, @Context HttpHeaders headers) {
        try {
            String token = headers.getHeaderString("Service-Token");
            if (token == null || token.isEmpty()) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Token não fornecido.\"}")
                        .build();
            }

            String resposta = chatService.enviarMensagem(mensagemChat.getChatId(), mensagemChat.getMensagem(), token);

            if (resposta != null) {
                return Response.ok("{\"response\":\"" + resposta + "\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"Não foi possível enviar a mensagem.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Endpoint para encerrar a sessão de chat
    @POST
    @Path("/end")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response encerrarChat(MensagemChat mensagemChat, @Context SecurityContext securityContext) {
        try {
            Principal principal = securityContext.getUserPrincipal();
            if (principal == null) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Usuário não autenticado.\"}")
                        .build();
            }

            String token = principal.getName();
            boolean sucesso = chatService.encerrarSessaoChat(mensagemChat.getChatId(), token);

            if (sucesso) {
                return Response.ok("{\"message\":\"Sessão de chat encerrada com sucesso.\"}").build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"Não foi possível encerrar a sessão de chat.\"}")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }

    // Classe interna para mapear o JSON recebido nas requisições
    public static class MensagemChat {
        @JsonProperty("chatId")
        private String chatId;

        @JsonProperty("mensagem")
        private String mensagem;

        // Getters e setters
        public String getChatId() {
            return chatId;
        }

        public void setChatId(String chatId) {
            this.chatId = chatId;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
