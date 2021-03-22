import ecc.*;
import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Operations.*;
import ecc.Weierstrass.ECC_Point_W_Jacob;
import ecc.Weierstrass.ECC_Point_W_Proj;

import java.math.BigInteger;

public class Main {

    public void testOCentury(){
        // Generate Curve
        ECC_Curve_W curve = Curves.Weierstrass_192();

        // Projective
        ECC_Point proj = new ECC_Point_W_Proj(curve, curve.getX(), curve.getY(), BigInteger.ONE);
        ECC_Key key = new ECC_Key(proj, curve.getN());
        ECC_Key key2 = new ECC_Key(proj, curve.getN());
        ECC_Point x1 = Scalar_Multiplication.doubleAndAdd(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point y1 = Scalar_Multiplication.doubleAndAdd(key2.getPublicKey(), key.getPrivateKey());

        // Jacobian
        ECC_Point jacob = new ECC_Point_W_Jacob(curve, curve.getX(), curve.getY(), BigInteger.ONE);
        key = new ECC_Key(jacob, curve.getN());
        key2 = new ECC_Key(jacob, curve.getN());
        ECC_Point x2 = Scalar_Multiplication.doubleAndAdd(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point y2 = Scalar_Multiplication.doubleAndAdd(key2.getPublicKey(), key.getPrivateKey());

    }

    public void testDIK(){



    }

    public static void main(String[] args){
        Main main = new Main();
        main.testOCentury();

        StringBuilder a = Scalar_Multiplication.wNAF(4, new BigInteger("1234567"));
        int b =2;
    }

}
