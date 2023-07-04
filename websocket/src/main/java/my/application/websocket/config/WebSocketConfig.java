package my.application.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.*;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public static class MyHandler extends TextWebSocketHandler {

        private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            String payload = message.getPayload();
            for (WebSocketSession webSocketSession : sessions) {
                webSocketSession.sendMessage(new TextMessage("hi" + payload));
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            sessions.remove(session);
        }
    }
    @Bean
    public MyHandler myHandler() {
        return new MyHandler();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(),"/room")
                .setAllowedOrigins("*");
    }
}
