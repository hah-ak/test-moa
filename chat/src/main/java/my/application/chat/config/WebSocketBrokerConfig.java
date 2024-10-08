package my.application.chat.config;

import lombok.RequiredArgsConstructor;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${allow.server.url}")
    private String[] allowServerUrl;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat/sub"); // 클라이언트가 subscribe 해야하는 메시지브로커의 토픽들
        registry.setApplicationDestinationPrefixes("/chat/pub"); // 클라이언트가 publish 해야하는 메시지브로커의 url 프리픽스(mapping 시 자동으로 붙을 prefix) controller의 massagemapping annotation에 정의된 path
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/connection") // 웹에서 stomp프로토콜로 접근하는 소켓서버를 연결하기위한 엔드포인트
                .setAllowedOriginPatterns(allowServerUrl);
//                .setAllowedOriginPatterns("127.0.0.1");
//                .addInterceptors(handshakeInterceptor());
    }

    // 만약 subscribe할때 검사할게 필요하다면..
    @Component
    @RequiredArgsConstructor
    public class SubscriptionInterceptor implements ChannelInterceptor {

        private final RedisSessionTemplate<String, Object> redisTemplate;

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            if (accessor != null && StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
                String destination = accessor.getDestination();
                String sessionId = accessor.getSessionId();

                assert destination != null;
                if (destination.startsWith("/chat/sub")) {

                }
            }
            return ChannelInterceptor.super.preSend(message, channel);
        }
    }

    //handshake시점에 필요한 코드...
    public HandshakeInterceptor handshakeInterceptor() {
        return new HandshakeInterceptor() {

            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//                return request.getHeaders().get("MEMBER_NUMBER") != null;
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

            }
        };
    }

}
