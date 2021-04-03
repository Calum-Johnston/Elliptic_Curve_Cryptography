import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_Montgomery;
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Operations.ECC_Key;
import ecc.ECC_Operations.Scalar_Multiplication;
import ecc.Points.Montgomery.ECC_Point_M_xz;
import ecc.Points.Weierstrass.ECC_Point_W_xz;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigInteger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class M_XZ_Tests {

    ECC_Curve_Montgomery curve;
    ECC_Point_M_xz basePoint;
    ECC_Key key;
    ECC_Key key2;

    @BeforeAll
    public void setupThis() {
        curve = Curves.Curve25519();
        basePoint = new ECC_Point_M_xz(curve, curve.getX(), curve.getY(), BigInteger.ONE);
        key = new ECC_Key(basePoint, curve.getN());
        key2 = new ECC_Key(basePoint, curve.getN());
    }

    @Test
    public void testProjective_DA() {
        ECC_Point_M_xz p1 = (ECC_Point_M_xz) Scalar_Multiplication.MontgomeryLadder((ECC_Point_M_xz) key.getPublicKey(), key2.getPrivateKey());
        ECC_Point_M_xz p2 = (ECC_Point_M_xz) Scalar_Multiplication.MontgomeryLadder((ECC_Point_M_xz) key2.getPublicKey(), key.getPrivateKey());
        BigInteger z1 = p1.getZ().modInverse(curve.getP());
        BigInteger z2 = p2.getZ().modInverse(curve.getP());
        BigInteger x1 = p1.getX().multiply(z1).mod(curve.getP());
        BigInteger x2 = p2.getX().multiply(z2).mod(curve.getP());
        assert(x1.equals(x2));
    }
}
