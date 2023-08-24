package my.application.security.entities.oauth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Entity(name = "authorization")
public class Authorization {

    @Id
    @Column
    private String id;
    private String registeredClientId;
    private String principalName;
    private String authorizationGrantType;
    @Column(length = 1000)
    private String authorizedScopes;
    @Column(length = 4000)
    private String attributes;
    @Column(length = 500)
    private String state;

    @Column(length = 4000)
    private String authorizationCodeValue;
    private Instant authorizationCodeIssuedAt;
    private Instant authorizationCodeExpiresAt;
    private String authorizationCodeMetadata;

    @Column(length = 4000)
    private String accessTokenValue;
    private Instant accessTokenIssuedAt;
    private Instant accessTokenExpiresAt;
    @Column(length = 2000)
    private String accessTokenMetadata;
    private String accessTokenType;
    @Column(length = 1000)
    private String accessTokenScopes;

    @Column(length = 4000)
    private String refreshTokenValue;
    private Instant refreshTokenIssuedAt;
    private Instant refreshTokenExpiresAt;
    @Column(length = 2000)
    private String refreshTokenMetadata;

    @Column(length = 4000)
    private String oidcIdTokenValue;
    private Instant oidcIdTokenIssuedAt;
    private Instant oidcIdTokenExpiresAt;
    @Column(length = 2000)
    private String oidcIdTokenMetadata;
    @Column(length = 2000)
    private String oidcIdTokenClaims;

    @Column(length = 4000)
    private String userCodeValue;
    private Instant userCodeIssuedAt;
    private Instant userCodeExpiresAt;
    @Column(length = 2000)
    private String userCodeMetadata;

    @Column(length = 4000)
    private String deviceCodeValue;
    private Instant deviceCodeIssuedAt;
    private Instant deviceCodeExpiresAt;
    @Column(length = 2000)
    private String deviceCodeMetadata;


    public Authorization() {}
    @Builder
    public Authorization(
            String id,
    String registeredClientId,
    String principalName,
    String authorizationGrantType,
    String authorizedScopes,
    String attributes,
    String state,
    String authorizationCodeValue,
    Instant authorizationCodeIssuedAt,
    Instant authorizationCodeExpiresAt,
    String authorizationCodeMetadata,
    String accessTokenValue,
    Instant accessTokenIssuedAt,
    Instant accessTokenExpiresAt,
    String accessTokenMetadata,
    String accessTokenType,
    String accessTokenScopes,
    String refreshTokenValue,
    Instant refreshTokenIssuedAt,
    Instant refreshTokenExpiresAt,
    String refreshTokenMetadata,
    String oidcIdTokenValue,
    Instant oidcIdTokenIssuedAt,
    Instant oidcIdTokenExpiresAt,
    String oidcIdTokenMetadata,
    String oidcIdTokenClaims,
    String userCodeValue,
    Instant userCodeIssuedAt,
    Instant userCodeExpiresAt,
    String userCodeMetadata,
    String deviceCodeValue,
    Instant deviceCodeIssuedAt,
    Instant deviceCodeExpiresAt,
    String deviceCodeMetadata
    ) {
        this.id = id;;
        this.registeredClientId = registeredClientId;;
        this.principalName = principalName;;
        this.authorizationGrantType = authorizationGrantType;;
        this.authorizedScopes = authorizedScopes;;
        this.attributes = attributes;;
        this.state = state;;
        this.authorizationCodeValue = authorizationCodeValue;;
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt;;
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt;;
        this.authorizationCodeMetadata = authorizationCodeMetadata;;
        this.accessTokenValue = accessTokenValue;;
        this.accessTokenIssuedAt = accessTokenIssuedAt;;
        this.accessTokenExpiresAt = accessTokenExpiresAt;;
        this.accessTokenMetadata = accessTokenMetadata;;
        this.accessTokenType = accessTokenType;;
        this.accessTokenScopes = accessTokenScopes;;
        this.refreshTokenValue = refreshTokenValue;;
        this.refreshTokenIssuedAt = refreshTokenIssuedAt;;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;;
        this.refreshTokenMetadata = refreshTokenMetadata;;
        this.oidcIdTokenValue = oidcIdTokenValue;;
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt;;
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt;;
        this.oidcIdTokenMetadata = oidcIdTokenMetadata;;
        this.oidcIdTokenClaims = oidcIdTokenClaims;;
        this.userCodeValue = userCodeValue;;
        this.userCodeIssuedAt = userCodeIssuedAt;;
        this.userCodeExpiresAt = userCodeExpiresAt;;
        this.userCodeMetadata = userCodeMetadata;;
        this.deviceCodeValue = deviceCodeValue;;
        this.deviceCodeIssuedAt = deviceCodeIssuedAt;;
        this.deviceCodeExpiresAt = deviceCodeExpiresAt;;
        this.deviceCodeMetadata = deviceCodeMetadata;;
    }
}
