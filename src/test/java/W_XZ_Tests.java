
import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Operations.ECC_Key;
import ecc.ECC_Operations.Scalar_Multiplication;
import ecc.Points.ECC_Point;
import ecc.Points.Weierstrass.ECC_Point_W_Proj;
import ecc.Points.Weierstrass.ECC_Point_W_xz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class W_XZ_Tests {

    ECC_Curve_W curve;
    ECC_Point_W_xz basePoint;
    ECC_Key key;
    ECC_Key key2;

    @BeforeAll
    public void setupThis() {
        curve = Curves.secp256r1();
        basePoint = new ECC_Point_W_xz(curve, curve.getX(), BigInteger.ONE);
        key = new ECC_Key(basePoint, curve.getN());
        key2 = new ECC_Key(basePoint, curve.getN());
    }

    @Test
    public void test_MontgomeryLadder() {
        ECC_Point_W_xz p1 = (ECC_Point_W_xz) Scalar_Multiplication.MontgomeryLadder((ECC_Point_W_xz) key.getPublicKey(), key2.getPrivateKey());
        ECC_Point_W_xz p2 = (ECC_Point_W_xz) Scalar_Multiplication.MontgomeryLadder((ECC_Point_W_xz) key2.getPublicKey(), key.getPrivateKey());
        BigInteger z1 = p1.getZ().modInverse(curve.getP());
        BigInteger z2 = p2.getZ().modInverse(curve.getP());
        BigInteger x1 = p1.getX().multiply(z1).mod(curve.getP());
        BigInteger x2 = p2.getX().multiply(z2).mod(curve.getP());
        assertTrue(x1.equals(x2));
    }

}
