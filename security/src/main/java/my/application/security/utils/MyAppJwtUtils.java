package my.application.security.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import java.time.Instant;

public class MyAppJwtUtils {

    public static String SECRET_KEY = "signature";

    public static Jwt createJwt(GoogleIdToken googleIdToken) {
        GoogleIdToken.Payload payload = googleIdToken.getPayload();
        return Jwt.withTokenValue("")
                .issuedAt(Instant.ofEpochSecond(payload.getIssuedAtTimeSeconds()))
                .expiresAt(Instant.ofEpochSecond(payload.getExpirationTimeSeconds()))
                .audience(payload.getAudienceAsList())
                .issuer(payload.getIssuer())
                .subject(payload.getSubject())
                .claim("email",payload.getEmail())
                .header("typ","JWT")
                .header("alg","HS256")
                .build();

    }
}
