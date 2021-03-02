package ecc;

import ecc.Weierstrass.ECC_Curve_Weierstrass;
import ecc.Weierstrass.ECC_Point_Aff;

import java.math.BigInteger;

public class ECDH {

    public static BigInteger computeSecret(ECC_Curve_Weierstrass curve, ECC_Point_Aff pubK, BigInteger priK){
        ECC_Point_Aff sharedK =  pubK.doubleAndAdd(BigInteger.TWO);
        return sharedK.getX();
    }

}
