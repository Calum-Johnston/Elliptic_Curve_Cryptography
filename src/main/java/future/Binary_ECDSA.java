package future;

import ecc.ECC_Operations.ECC_Signature;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Binary_ECDSA {
    public static ECC_Signature Sign(BinaryField_Curve curve, BinaryECC_Key kp, String message){

        // Select random integer 1 <= k <= n - 1
        Random rnd = new Random();
        BigInteger k;
        do{
            k = new BigInteger(curve.getN().bitLength(), rnd);
            k = new BigInteger("11",2);
        } while(k.compareTo(curve.getN()) > 0); // k can be 0 currently

        // Compute hash of message and convert to integer
        BigInteger e = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
            e = new BigInteger("1010", 2);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }

        // Compute KG = (x1, y1)
        BinaryField_Point kG = curve.getG().pointMultiplication(k);

        // Compute r = x1 mod n
        BigInteger r = kG.getX().mod(curve.getN());

        // Compute k-1 mod n
        BigInteger inverseK = k.modInverse(curve.getN());

        // Compute s = k-1(e + pK * r) mod n
        BigInteger pKr = r.multiply(kp.getPrivateKey());
        BigInteger epKr = e.add(pKr);
        BigInteger s = inverseK.multiply(epKr);
        s = s.mod(curve.getN());

        return new ECC_Signature(r, s);
    }

    public static boolean Verify(BinaryField_Curve curve, ECC_Signature sig, BinaryECC_Key kp, String message){

        // Verify r and s are integers in interval [1, n-1]

        // Compute hash of message and convert to integer
        BigInteger e = new BigInteger("0");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
            e = new BigInteger("1010", 2);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }

        // Compute w = s-1 mod n
        BigInteger w = sig.getS().modInverse(curve.getN());

        // Compute u1 = ew mod n and u2 = rw mod n
        BigInteger u1 = (e.multiply(w)).mod(curve.getN());
        BigInteger u2 = (sig.getR().multiply(w)).mod(curve.getN());

        // Compute X = u1G + u2PK
        BinaryField_Point u1G = curve.getG().pointMultiplication(u1);
        BinaryField_Point u2PK = kp.getPublicKey().pointMultiplication(u2);
        BinaryField_Point X = u1G.pointAddition(u2PK);

        // Check x coordinate of X equals r
        return X.getX().equals(sig.getR());
    }

}
