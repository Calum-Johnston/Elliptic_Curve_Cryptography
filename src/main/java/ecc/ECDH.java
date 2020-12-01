package ecc;

import java.math.BigInteger;

public class ECDH {

    public static BigInteger computeSecret(ECC_Curve curve, ECC_Point pubK, BigInteger priK){
        ECC_Point sharedK = pubK.pointMultiplication(priK, curve.getP(), curve.getA());
        return sharedK.getX();
    }

}
