package my.application.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chatting"); // 클라이언트가 subscribe 해야하는 메시지브로커의 토픽들
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 publish 해야하는 메시지브로커의 url 프리픽스(mapping 시 자동으로 붙을 prefix) controller의 massagemapping annotation에 정의된 path
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-conn") // 웹에서 stomp프로토콜로 접근하는 소켓서버를 연결하기위한 엔드포인트
                .setAllowedOriginPatterns("http://localhost:[*]", "ws://localhost:[*]");
    }

}
