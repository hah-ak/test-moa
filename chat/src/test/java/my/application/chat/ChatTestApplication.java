package my.application.chat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.profiles.active=local"})
public class ChatTestApplication {
    @LocalServerPort
    private int port;
    private WebSocketStompClient webSocketClient;

    @BeforeEach
    public void setUp() {
        this.webSocketClient = new WebSocketStompClient(new StandardWebSocketClient());
        this.webSocketClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void testEchoWebSocketClient() throws URISyntaxException, ExecutionException, InterruptedException, TimeoutException {

        final CompletableFuture<String> resultFuture = new CompletableFuture<>();

        String url = "ws://localhost:" + port + "/chat/connection";
        StompSession session = webSocketClient.connect(url, new StompSessionHandlerAdapter() {}).get(5, TimeUnit.SECONDS);

        session.subscribe("/topic/messages", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                resultFuture.complete((String) payload);
            }
        });

        String message = "Test Message";
        session.send("/app/chat", message);

        String result = resultFuture.get(5, TimeUnit.SECONDS);
        assertEquals(message, result);


    }
}
