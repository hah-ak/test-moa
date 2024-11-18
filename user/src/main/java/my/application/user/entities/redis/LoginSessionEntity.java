package my.application.user.entities.redis;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("login")
public class LoginSessionEntity {

    @Id
    private String session;
    private String signature;
    private String email;
    private String loginIp;
    private LocalDateTime loginTime;

}
