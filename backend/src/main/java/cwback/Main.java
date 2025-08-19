package cwback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
/**
 * Main class for starting the Spring Boot application.
 * Implements WebSocketConfigurer to configure WebSocket handlers.
 */
@SpringBootApplication
@EnableWebSocket
public class Main implements WebSocketConfigurer {

    private TicketPool ticketPool;
    private Thread vendorThread;
    private Thread customerThread;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    /**
     * Registers WebSocket handlers for the application.
     * @param registry WebSocketHandlerRegistry to configure handlers.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/ws/config").setAllowedOrigins("*");
    }
    /**
     * Bean definition for the WebSocketHandler.
     * @return WebSocketHandler instance.
     */
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandler();
    }
}
