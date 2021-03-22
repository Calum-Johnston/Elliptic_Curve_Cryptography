package ecc.ECC_Operations;

// https://cryptobook.nakov.com/digital-signatures/ecdsa-sign-verify-messages

public class ECDSA {

   /** public static ECC_Signature Sign(ECC_Curve_Weierstrass curve, ECC_Key kp, String message){

        // Select random integer 1 <= k <= n - 1
        Random rnd = new Random();
        BigInteger k;
        do{
            k = new BigInteger(curve.getN().bitLength(), rnd);
        } while(k.compareTo(curve.getN()) > 0);

        // Compute hash of message and convert to integer
        BigInteger e = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }

        // Compute kG = (x1, y1)
        ECC_Point_Weierstrass_Aff kG = curve.getG().doubleAndAdd(k);

        // Computer r = x1 mod n
        BigInteger r = kG.getX().mod(curve.getN());

        // Compute k-1 mod n
        BigInteger inverseK = k.modInverse(curve.getN());

        // Compute s = k-1(e + pK * r) mod n
        BigInteger pKr = r.multiply(kp.getPrivateKey());
        BigInteger epKr = e.add(pKr);
        BigInteger s = inverseK.multiply(epKr);
        s = s.mod(curve.getN());

        // Return value
        return new ECC_Signature(r, s);
    }

    public static boolean Verify(ECC_Curve_Weierstrass curve, ECC_Signature sig, ECC_Key kp, String message){

        // Verify r and s are integers in interval [1, n-1]

        // Compute hash of message and convert to integer
        BigInteger e = new BigInteger("0");
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
            e = new BigInteger(1, hash);
        } catch (NoSuchAlgorithmException f) {
            f.printStackTrace();
        }

        // Compute w = s-1 mod n
        BigInteger w = sig.getS().modInverse(curve.getN());

        // Compute u1 = ew mod n and u2 = rw mod n
        BigInteger u1 = (e.multiply(w)).mod(curve.getN());
        BigInteger u2 = (sig.getR().multiply(w)).mod(curve.getN());

        // Compute X = u1G + u2PK
        ECC_Point_Weierstrass_Aff u1G = curve.getG().doubleAndAdd(u1);
        ECC_Point_Weierstrass_Aff u2PK = kp.getPublicKey().doubleAndAdd(u2);
        ECC_Point_Weierstrass_Aff X = u1G.pointAddition(u2PK);

        // Check x coordinate of X equals r
        return X.getX().equals(sig.getR());
    }*/

}
