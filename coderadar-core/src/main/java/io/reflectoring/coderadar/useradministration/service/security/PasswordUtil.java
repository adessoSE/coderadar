package io.reflectoring.coderadar.useradministration.service.security;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
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
      logger.error("Error while setting key: {}", e.toString());
    }
  }

  public static byte[] encrypt(String messageToEncrypt) {
    if (messageToEncrypt != null) {
      try {
        byte[] plainBytes = messageToEncrypt.getBytes();
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);

        byte[] iv = new byte[12];
        secureRandom.nextBytes(iv);

        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        byte[] cipherText = cipher.doFinal(plainBytes);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + cipherText.length);
        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        return byteBuffer.array();
      } catch (Exception e) {
        logger.error("Error while encrypting: {}", e.toString());
      }
    }
    return null;
  }

  public static String decrypt(byte[] cipherMessage) {
    if (cipherMessage != null) {
      try {
        ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
        int ivLength = byteBuffer.getInt();
        if (ivLength < 12 || ivLength >= 16) {
          throw new IllegalArgumentException("Invalid iv length");
        }
        byte[] iv = new byte[ivLength];
        byteBuffer.get(iv);
        byte[] cipherText = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherText);

        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(128, iv));

        byte[] plainText = cipher.doFinal(cipherText);
        return new String(plainText);
      } catch (Exception e) {
        logger.error("Error while decrypting: {}", e.toString());
      }
    }
    return null;
  }
}
