package my.application.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.bouncycastle.asn1.pkcs.RSAPublicKey;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.crypto.Mac;
import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.spec.RSAPublicKeySpec;
import java.time.Instant;

public class MyAppJwtUtils {

    public static String SECRET_KEY = "signature";

//    public static Jwt createJwt(GoogleIdToken googleIdToken) {
//        GoogleIdToken.Payload payload = googleIdToken.getPayload();
//        return Jwt.withTokenValue("")
//                .issuedAt(Instant.ofEpochSecond(payload.getIssuedAtTimeSeconds()))
//                .expiresAt(Instant.ofEpochSecond(payload.getExpirationTimeSeconds()))
//                .audience(payload.getAudienceAsList())
//                .issuer(payload.getIssuer())
//                .subject(payload.getSubject())
//                .claim("email",payload.getEmail())
//                .header("typ","JWT")
//                .header("alg","HS256")
//                .build();
//    }

    public static String encodeJwt(Jwt jwt) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        Base64 base64 = new Base64();
        String s = base64.encodeAsString(objectMapper.writeValueAsString(jwt.getHeaders()).getBytes(StandardCharsets.UTF_8));
        String s1 = base64.encodeAsString(objectMapper.writeValueAsString(jwt.getClaims()).getBytes(StandardCharsets.UTF_8));

        Mac mac = HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, "secretawfawfe".getBytes());
        mac.update((s + "." + s1).getBytes());
        byte[] bytes = mac.doFinal();


        return new String(bytes, StandardCharsets.UTF_8);
    }

    private static void secretKey() throws KeyStoreException {

    }

    public static void createJWS() {

        JWSHeader build = new JWSHeader.Builder(JWSAlgorithm.RS512)
                .type(JOSEObjectType.JWT)
                .build();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("")
                .issuer("")
                .claim("","")
                .claim("","")
                .build();

        SignedJWT signedJWT = new SignedJWT(build, claimsSet);

//        new RSASSASigner();
    }
}
