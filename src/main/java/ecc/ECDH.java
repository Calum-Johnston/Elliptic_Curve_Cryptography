package ecc;

import java.math.BigInteger;

public class ECDH {

    public static BigInteger computeSecret(ECC_Curve curve, ECC_Point pubK, BigInteger priK){
        ECC_Point sharedK = pubK.pointMultiplication(priK);
        return sharedK.getX();
    }

    public static BigInteger computeSecret(BinaryField_Curve curve, BinaryField_Point pubK, BigInteger priK){
        BinaryField_Point sharedK = pubK.pointMultiplication(priK);
        return sharedK.getX();
    }

}
