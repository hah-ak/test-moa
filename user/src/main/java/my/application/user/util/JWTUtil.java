package my.application.user.util;

import com.google.common.base.VerifyException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import my.application.user.services.member.MemberSignInUserDetails;
import my.secret.source.SecretKeyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class JWTUtil {
    private static final KeyStore keyStore;
    private static final PrivateKey PRIVATE_KEY;
    private static final JWSSigner JWS_SIGNER;
    private static final PublicKey PUBLIC_KEY;
    private static final Certificate CERTIFICATE;
    private static final JWSHeader JWS_HEADER;
    private static final JWSVerifier JWS_VERIFIER;

    static {
        // file inputstream은 파일시스템아 맞춰서, 다른 스트림은 빌드클래스패스에 맞춰서 찾는데 classloader를 쓰는경우에는 클래스패스에서 /없이 써야하고
        // 아니면 그냥 클래스패스 기준으로 절대경로로사용
        try(InputStream inputStream = JWTUtil.class.getResourceAsStream("/"+SecretKeyUtil.jWESecretProperties.getPath())) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, SecretKeyUtil.jWESecretProperties.getPassword().toCharArray());
            PRIVATE_KEY = (PrivateKey) keyStore.getKey("1", SecretKeyUtil.jWESecretProperties.getPassword().toCharArray());
            CERTIFICATE = keyStore.getCertificate( "1");
            PUBLIC_KEY = CERTIFICATE.getPublicKey();
            JWS_SIGNER = new RSASSASigner(PRIVATE_KEY);
            JWS_VERIFIER = new RSASSAVerifier((RSAPublicKey) PUBLIC_KEY);
            JWS_HEADER = new JWSHeader.Builder(JWSAlgorithm.RS512)
                    .type(JOSEObjectType.JWT)
                    .build();
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException |
                 UnrecoverableKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public static SignedJWT parseJWT(String jwt) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(jwt);
        if (signedJWT.verify(JWS_VERIFIER)) {
            return signedJWT;
        } else {
            throw new VerifyException("JWT verification failed");
        }
    }

    public static String createJWSToken(MemberSignInUserDetails details) throws JOSEException {

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject("")
                .issuer("hahak.com")
                .claim("email", details.getUsername())
                .claim("name", details.getUserNickName())
                .build();

        SignedJWT signedJWT = new SignedJWT(JWS_HEADER, claimsSet);
        signedJWT.sign(JWS_SIGNER);

        return signedJWT.serialize();
    }
}
