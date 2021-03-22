
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Operations.Scalar_Multiplication;
import ecc.ECC_Point;
import ecc.Weierstrass.ECC_Point_W_Proj;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

public class Weierstrass_Tests {

    ECC_Curve_W curve;
    ECC_Point basePoint;
    ECC_Point kPoint;
    BigInteger multiple = new BigInteger("3");

    @Before
    public void setupThis() {
        // Setup curve
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF",16);
        BigInteger a = new BigInteger("-3");
        BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1",16);
        BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012",16);
        BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811",16);
        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831",16);
        BigInteger h = new BigInteger("1");
        curve = new ECC_Curve_W(p, a, b, x, y, n, h);

        // Setup base point and multiple
        basePoint = curve.getG();

        // Perform basic multiplication
        kPoint = Scalar_Multiplication.doubleAndAdd(basePoint, multiple);
    }

    @Test
    public void testProjective() {

    }

    @After
    public void tearThis() {
    }


}
