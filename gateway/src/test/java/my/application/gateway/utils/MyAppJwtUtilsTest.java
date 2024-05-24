package my.application.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

class MyAppJwtUtilsTest {


    Jwt createJwt() {

        return Jwt.withTokenValue("secret")
                .issuedAt(Instant.ofEpochSecond(1909920298))
                .expiresAt(Instant.ofEpochSecond(1909929999))
//                .audience(payload.getAudienceAsList())
//                .issuer(payload.getIssuer())
//                .subject(payload.getSubject())
                .claim("email","abcd")
                .header("typ","JWT")
                .header("alg","HS256")
                .build();
    }

    @Test
    void encodeJwt() throws JsonProcessingException {
        String s = MyAppJwtUtils.encodeJwt(createJwt());
        System.out.println(s);
    }
}