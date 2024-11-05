package br.com.fiap.util;

import br.com.fiap.validacao.ValidarToken;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class FiltroToken implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.equals("autofix/login/autenticar") ||
                path.equals("autofix/login-oficina/autenticar") ||
                path.equals("autofix/cliente/cadastrar") ||
                path.equals("autofix/oficina/cadastrar") ||
                path.equals("chat/init") ||
                path.equals("chat/send") ||
                path.equals("chat/end")) {
            return;
        }

        String authorizationHeader = requestContext.getHeaderString("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token de autenticação é necessário.\"}")
                    .build());
            return;
        }

        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {
            String userId = ValidarToken.extractUserId(token);
            if (!ValidarToken.validateToken(token, userId)) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"error\":\"Token de autenticação inválido ou expirado.\"}")
                        .build());
                return;
            }
            requestContext.setProperty("userId", userId);
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"error\":\"Token de autenticação inválido.\"}")
                    .build());
        }
    }
}
