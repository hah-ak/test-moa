package my.domain.redis.entities.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@RedisHash("credentialToken")
public class CredentialToken implements Serializable {

    @Id //springframework id 사용
    private String key;
    private String accessToken;
    private String refreshToken;
    private Long expirationTimeMilliseconds;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public CredentialToken(){}

    @Builder
    public CredentialToken(StoredCredential storedCredential) {
        this.accessToken = storedCredential.getAccessToken();
        this.refreshToken = storedCredential.getRefreshToken();
        this.expirationTimeMilliseconds = storedCredential.getExpirationTimeMilliseconds();
    }

    @Builder
    public CredentialToken(String key, StoredCredential storedCredential) {
        this.key = key;
        this.accessToken = storedCredential.getAccessToken();
        this.refreshToken = storedCredential.getRefreshToken();
        this.expirationTimeMilliseconds = storedCredential.getExpirationTimeMilliseconds();
    }
}
