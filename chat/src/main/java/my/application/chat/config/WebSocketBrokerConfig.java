package my.application.chat.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${allow.server.url}")
    private String[] allowServerUrl;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/chat/chat"); // 클라이언트가 subscribe 해야하는 메시지브로커의 토픽들
        registry.setApplicationDestinationPrefixes("/chat/pub"); // 클라이언트가 publish 해야하는 메시지브로커의 url 프리픽스(mapping 시 자동으로 붙을 prefix) controller의 massagemapping annotation에 정의된 path
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat/connection") // 웹에서 stomp프로토콜로 접근하는 소켓서버를 연결하기위한 엔드포인트
                .setAllowedOriginPatterns(allowServerUrl);
    }

}
