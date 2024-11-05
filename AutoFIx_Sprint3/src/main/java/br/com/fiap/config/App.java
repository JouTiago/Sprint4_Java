package br.com.fiap.config;

import javax.ws.rs.ApplicationPath;
import br.com.fiap.service.chat.ChatService;
import br.com.fiap.service.chat.ChatServiceImpl;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


@ApplicationPath("/autofix")
public class App extends ResourceConfig {
    public App() {
        packages("br.com.fiap");
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(ChatServiceImpl.class).to(ChatService.class);
            }
        });
    }
}
