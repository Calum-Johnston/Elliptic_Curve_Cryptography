package rsa;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RSA {

    public static BigInteger Sign(String message, BigInteger d, BigInteger n){
        BigInteger digestValue = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            digestValue = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }
        BigInteger s = digestValue.modPow(d, n);
        return s;
    }

    public static boolean Verify(String message, BigInteger s, BigInteger e, BigInteger n){
        BigInteger digestValue = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            digestValue = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }
        BigInteger originalDigestValue = s.modPow(e, n);
        return digestValue.equals(originalDigestValue);
    }

}
