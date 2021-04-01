
import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_W;
import ecc.ECC_Operations.ECC_Key;
import ecc.ECC_Operations.Scalar_Multiplication;
import ecc.Points.ECC_Point;
import ecc.Points.Weierstrass.ECC_Point_W_Proj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class W_Proj_Tests {

    ECC_Curve_W curve;
    ECC_Point basePoint;
    ECC_Key key;
    ECC_Key key2;

    @BeforeAll
    public void setupThis() {
        curve = Curves.secp256r1();
        basePoint = new ECC_Point_W_Proj(curve, curve.getX(), curve.getY(), BigInteger.ONE);
        key = new ECC_Key(basePoint, curve.getN());
        key2 = new ECC_Key(basePoint, curve.getN());
    }

    @Test
    public void testProjective_DA() {
        ECC_Point p1 = Scalar_Multiplication.doubleAndAdd(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.doubleAndAdd(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_W_Proj p1_ = ((ECC_Point_W_Proj) p1).convertAffine();
        ECC_Point_W_Proj p2_ = ((ECC_Point_W_Proj) p2).convertAffine();

        BigInteger left = curve.getY().pow(2).mod(curve.getP());
        BigInteger right = curve.getX().pow(3).mod(curve.getP());
        right = right.add(curve.getA().multiply(curve.getX())).mod(curve.getP());
        right = right.add(curve.getB()).mod(curve.getP());

        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void testProjective_BinaryNAF(){
        ECC_Point p1 = Scalar_Multiplication.binaryNAF(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.binaryNAF(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_W_Proj p1_ = ((ECC_Point_W_Proj) p1).convertAffine();
        ECC_Point_W_Proj p2_ = ((ECC_Point_W_Proj) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void testProjective_WindowNAF(){
        ECC_Point p1 = Scalar_Multiplication.windowNAF(key.getPublicKey(), key2.getPrivateKey(), 4);
        ECC_Point p2 = Scalar_Multiplication.windowNAF(key2.getPublicKey(), key.getPrivateKey(), 4);
        ECC_Point_W_Proj p1_ = ((ECC_Point_W_Proj) p1).convertAffine();
        ECC_Point_W_Proj p2_ = ((ECC_Point_W_Proj) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void testProjective_SlidingNAF(){
        ECC_Point p1 = Scalar_Multiplication.slidingWindow(key.getPublicKey(), key2.getPrivateKey(), 4);
        ECC_Point p2 = Scalar_Multiplication.slidingWindow(key2.getPublicKey(), key.getPrivateKey(), 4);
        ECC_Point_W_Proj p1_ = ((ECC_Point_W_Proj) p1).convertAffine();
        ECC_Point_W_Proj p2_ = ((ECC_Point_W_Proj) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void testProjective_Unified(){
        ECC_Point p1 = Scalar_Multiplication.binaryNAF_Uni(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.binaryNAF_Uni(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_W_Proj p1_ = ((ECC_Point_W_Proj) p1).convertAffine();
        ECC_Point_W_Proj p2_ = ((ECC_Point_W_Proj) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }




}
