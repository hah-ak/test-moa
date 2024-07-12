package my.application.member.util;

import com.google.common.base.VerifyException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import web.core.util.SecretKeyUtil;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;

public class JWTUtil {
    private static final KeyStore keyStore;
    private static final PublicKey PUBLIC_KEY;
    private static final Certificate CERTIFICATE;
    private static final JWSVerifier JWS_VERIFIER;

    static {
        try(InputStream inputStream = JWTUtil.class.getResourceAsStream("/"+ SecretKeyUtil.jWESecretProperties.getPath())) {
            keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, SecretKeyUtil.jWESecretProperties.getPassword().toCharArray());
            CERTIFICATE = keyStore.getCertificate( "1");
            PUBLIC_KEY = CERTIFICATE.getPublicKey();
            JWS_VERIFIER = new RSASSAVerifier((RSAPublicKey) PUBLIC_KEY);
        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException e) {
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
}
