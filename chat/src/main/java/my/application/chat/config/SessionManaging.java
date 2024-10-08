package my.application.chat.config;

import lombok.RequiredArgsConstructor;
import my.domain.redis.RedisSessionTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionManaging {

    private final RedisSessionTemplate<String, Object> redisTemplate;

    @EventListener
    public void processSessionCreatedEvent(SessionCreatedEvent event) {
        redisTemplate.opsForSet().add(event.getSessionId(), event.getSession());
    }
    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        // do the necessary work
        redisTemplate.opsForSet().remove(event.getSessionId(), event.getSession());
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        // do the necessary work
        redisTemplate.opsForSet().remove(event.getSessionId(), event.getSession());
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        // do the necessary work
        redisTemplate.opsForSet().remove(event.getSessionId(), event.getSession());
    }
}
