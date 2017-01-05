package org.wickedsource.coderadar.security.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;

/**
 * Service for generation and holding a secret key. The generated key is a AES key of the length 256 bit and it is used to sign and to verify authentication
 * tokens. The key is generated once by starting application and deleted when the application is shut down, so all tokens become invalid in this case.
 */
@Service
public class SecretKeyService {

    private static final String ALGORITHM = "AES";
    private static final int KEY_LENGHT = 256;
    private static SecretKey secretKey;

    @PostConstruct
    public void initSecretKey() {
        secretKey = generateSecretKey();
    }

    private SecretKey generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            keyGenerator.init(KEY_LENGHT);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not generate secret authentication token key");
        }
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}
