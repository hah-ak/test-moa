package my.domain.redis.entities.google;

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
    private String idToken;
    private String scope;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public CredentialToken(){}

    @Builder
    public CredentialToken(String accessToken, String idToken, String scope, String tokenType, String refreshToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.scope = scope;
        this.tokenType = tokenType;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    @Builder
    public CredentialToken(String key, CredentialToken credentialToken) {
        this.key = key;
        this.accessToken = credentialToken.getAccessToken();
        this.idToken = credentialToken.getIdToken();
        this.scope = credentialToken.getScope();
        this.tokenType = credentialToken.getTokenType();
        this.refreshToken = credentialToken.getRefreshToken();
        this.expiresIn = credentialToken.getExpiresIn();
    }
}
