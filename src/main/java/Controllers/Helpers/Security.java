package Controllers.Helpers;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import Controllers.Enum.Config;

public class Security {

    public static String generateKeyBase64String() {
        byte[] randomBytes = new byte[16];
        new SecureRandom().nextBytes(randomBytes);
        return Base64.getEncoder().encodeToString(randomBytes);
    }

    public static SecretKey getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(Config.SECRET_KEY.getValue());
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String encrypt(String data) {
        return encrypt(data, getKey());
    }

    public static String encrypt(String data, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public static String decrypt(String encryptedData) {
        return decrypt(encryptedData, getKey());
    }

    public static String decrypt(String encryptedData, SecretKey secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
