package picapoint.picapointServer.util;

import com.auth0.jwt.algorithms.Algorithm;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class JwtTokenHandler {
    private static Algorithm algorithm;

    private JwtTokenHandler() {

    }

    private static Algorithm algorithmBuilder() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);
        KeyPair kp = keyGenerator.genKeyPair();
        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();
        return Algorithm.RSA256((RSAPublicKey) publicKey, (RSAPrivateKey) privateKey);
    }

    public static Algorithm getAlgorithm() throws NoSuchAlgorithmException {
        if (algorithm == null) algorithm = algorithmBuilder();
        return algorithm;
    }
}
