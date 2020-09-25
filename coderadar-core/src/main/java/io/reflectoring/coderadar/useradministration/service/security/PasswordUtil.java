package io.reflectoring.coderadar.useradministration.service.security;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Service for working with passwords, for example hashing or verification. */
public class PasswordUtil {

  private static SecretKeySpec secretKey;
  private static final Logger logger = LoggerFactory.getLogger(PasswordUtil.class);

  private PasswordUtil() {}

  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * Hash the password with "bcrypt".
   *
   * @param password the password to be hashed
   * @return hashed password as hexadecimal
   */
  public static String hash(String password) {
    return passwordEncoder.encode(password);
  }

  public static void setKey(String myKey) {
    MessageDigest sha;
    try {
      byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

    public static String encrypt(String strToEncrypt) {
    if(strToEncrypt == null) {
      return null;
    }
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));

    } catch (Exception e) {
      logger.error("Error while encrypting: {}", e.toString());
    }
    return null;
  }

  public static String decrypt(String strToDecrypt) {
    if(strToDecrypt == null) {
      return null;
    }
    try {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      logger.error("Error while decrypting: {}", e.toString());
    }
    return null;
  }

}
