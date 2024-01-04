package my.application.gateway.entities.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
@Getter
@RedisHash("credentialToken")
public class CredentialToken {

    @Id //springframework id 사용
    private String key;
    private Long expirationTimeMilliseconds;
    private StoredCredential storedCredential;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public CredentialToken(){
        super();
    }

    @Builder
    public CredentialToken(StoredCredential storedCredential) {
        super();
        this.storedCredential = storedCredential;
        this.expirationTimeMilliseconds = storedCredential.getExpirationTimeMilliseconds();
    }

    @Builder
    public CredentialToken(String key, StoredCredential storedCredential) {
        super();
        this.key = key;
        this.storedCredential = storedCredential;
        this.expirationTimeMilliseconds = storedCredential.getExpirationTimeMilliseconds();
    }
}
