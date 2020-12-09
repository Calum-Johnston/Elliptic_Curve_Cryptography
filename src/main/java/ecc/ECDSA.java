package ecc;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

// https://cryptobook.nakov.com/digital-signatures/ecdsa-sign-verify-messages

public class ECDSA {

    public static ECC_Signature Sign(ECC_Curve curve, ECC_Key kp, String message){
        BigInteger e = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }
        Random rnd = new Random();
        BigInteger k;
        do{
            k = new BigInteger(curve.getN().bitLength(), rnd);
        } while(k.compareTo(curve.getN()) > 0);
        ECC_Point kG = curve.getG().pointMultiplication(k, curve.getP(), curve.getA());
        BigInteger r = kG.getX().mod(curve.getN());
        BigInteger inverseK = k.modInverse(curve.getN());
        BigInteger rPK = r.multiply(kp.getPrivateKey());
        BigInteger erPK = e.add(rPK);
        BigInteger s = inverseK.multiply(erPK);
        s = s.mod(curve.getN());
        return new ECC_Signature(r, s);
    }

    public static boolean Verify(ECC_Curve curve, ECC_Signature sig, ECC_Key kp, String message){
        BigInteger e = new BigInteger("0");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }
        BigInteger s1 = sig.getS().modInverse(curve.getN());
        BigInteger u1 = (e.multiply(s1)).mod(curve.getN());
        BigInteger u2 = (sig.getR().multiply(s1)).mod(curve.getN());
        ECC_Point u1G = curve.getG().pointMultiplication(u1, curve.getP(), curve.getA());
        ECC_Point u2PK = kp.getPublicKey().pointMultiplication(u2, curve.getP(), curve.getA());
        ECC_Point R = u1G.pointAddition(u2PK.getX(), u2PK.getY(), curve.getP());
        return R.getX().equals(sig.getR());
    }

    public static ECC_Signature Sign(BinaryField_Curve curve, BinaryECC_Key kp, String message){
        BigInteger e = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }
        Random rnd = new Random();
        BigInteger k;
        do{
            k = new BigInteger(curve.getN().bitLength(), rnd);
        } while(k.compareTo(curve.getN()) > 0);
        BinaryField_Point kG = curve.getG().pointMultiplication(k);
        BigInteger r = kG.getX().mod(curve.getN());
        BigInteger inverseK = k.modInverse(curve.getN());
        BigInteger rPK = r.multiply(kp.getPrivateKey());
        BigInteger erPK = e.add(rPK);
        BigInteger s = inverseK.multiply(erPK);
        s = s.mod(curve.getN());
        return new ECC_Signature(r, s);
    }


}
