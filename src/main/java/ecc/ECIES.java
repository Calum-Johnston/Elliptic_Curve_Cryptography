package ecc;

import java.math.BigInteger;

public class ECIES {

    public static void Encrypt(ECC_Curve curve, ECC_Point_Aff vPK, BigInteger uPk, String message){

        // Key Agreement Function
        BigInteger sharedSecret = ECDH.computeSecret(curve, vPK, uPk);

        // Key Derivation Function

    }

}
