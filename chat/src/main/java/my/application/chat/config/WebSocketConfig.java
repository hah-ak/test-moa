package my.application.chat.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


//implements WebSocketConfigurer
//@Configuration//@EnableWebSocket
public class WebSocketConfig  {

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
            System.out.println(" close ");
            sessions.remove(session);
        }
    }

    public static class MyBinarySocketHandler implements WebSocketHandler {

        private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            sessions.add(session);
        }

        @Override
        public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            ByteBuffer payload = (ByteBuffer) message.getPayload();
            for (WebSocketSession socketSession : sessions) {
                socketSession.sendMessage(new BinaryMessage(payload));
            }
        }

        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
            sessions.remove(session);
        }

        @Override
        public boolean supportsPartialMessages() {
            return true;
        }
    }

    public static class MyInterceptor implements HandshakeInterceptor {
        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

            HttpHeaders headers = request.getHeaders();
            headers.forEach((key, value) -> {
//                System.out.printf("header key : %s, header value : %s%n", key,value);
            });
            return true;
        }

        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {}
    }
//    @Bean
//    public MyHandler myHandler() {
//        return new MyHandler();
//    }
//
//    @Bean
//    public MyBinarySocketHandler myBinarySocketHandler() { return new MyBinarySocketHandler();}
//
//    @Bean
//    public MyInterceptor myInterceptor() {
//        return new MyInterceptor();
//    }
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(myBinarySocketHandler(),"/room")
//                .setAllowedOrigins("*")
//                .addInterceptors(myInterceptor());
//    }


}
