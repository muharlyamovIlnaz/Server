package com.ilnaz.server.util;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
public class CryptoUtils {


    public static String generateStrongPasswordHash(String password, byte[] salt) throws Exception {
        int iterations = 10000;
        char[] chars = password.toCharArray();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static byte[] getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static boolean validatePassword(String originalPassword, String storedPasswordHash, byte[] salt) throws Exception {
        String hashedPassword = generateStrongPasswordHash(originalPassword, salt);
        return hashedPassword.equals(storedPasswordHash);
    }
}
