
import ecc.Curves.Curves;
import ecc.Curves.ECC_Curve_Ed;
import ecc.ECC_Operations.ECC_Key;
import ecc.ECC_Operations.Scalar_Multiplication;
import ecc.Points.ECC_Point;
import ecc.Points.Edwards.ECC_Point_Ed_Inv;
import ecc.Points.Edwards.ECC_Point_Ed_Inv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Ed_Inv_Tests {

    ECC_Curve_Ed curve;
    ECC_Point basePoint;
    ECC_Key key;
    ECC_Key key2;

    @BeforeAll
    public void setupThis() {
        curve = Curves.Ed25519();
        basePoint = new ECC_Point_Ed_Inv(curve, curve.getY(), curve.getX(), curve.getX().multiply(curve.getY().mod(curve.getP())));
        key = new ECC_Key(basePoint, curve.getN());
        key2 = new ECC_Key(basePoint, curve.getN());
    }

    // Test schemes

    @Test
    public void test_DoubleandAdd() {
        ECC_Point p1 = Scalar_Multiplication.doubleAndAdd(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.doubleAndAdd(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_BinaryNAF(){
        ECC_Point p1 = Scalar_Multiplication.binaryNAF(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.binaryNAF(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_wNAF(){
        ECC_Point p1 = Scalar_Multiplication.windowNAF(key.getPublicKey(), key2.getPrivateKey(), 4);
        ECC_Point p2 = Scalar_Multiplication.windowNAF(key2.getPublicKey(), key.getPrivateKey(), 4);
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_SlidingNAF(){
        ECC_Point p1 = Scalar_Multiplication.slidingWindow(key.getPublicKey(), key2.getPrivateKey(), 4);
        ECC_Point p2 = Scalar_Multiplication.slidingWindow(key2.getPublicKey(), key.getPrivateKey(), 4);
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_fractionalwNAF(){
        int q = 5;
        int w0 = (int) Math.floor(((Math.log(q) / Math.log(2)) + 2));
        int r = q - (int) Math.pow(2, w0-2);
        double w1 = r/Math.pow(2, w0-2);
        ECC_Point p1 = Scalar_Multiplication.fractional_wNAF(key.getPublicKey(), key2.getPrivateKey(), w0, w1);
        ECC_Point p2 = Scalar_Multiplication.fractional_wNAF(key2.getPublicKey(), key.getPrivateKey(), w0, w1);
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_BinaryNOF(){
        ECC_Point p1 = Scalar_Multiplication.binaryNOF(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.binaryNOF(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_wNOF(){
        ECC_Point p1 = Scalar_Multiplication.windowNOF(key.getPublicKey(), key2.getPrivateKey(), 4);
        ECC_Point p2 = Scalar_Multiplication.windowNOF(key2.getPublicKey(), key.getPrivateKey(), 4);
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_fractionalwNOF(){
        int q = 5;
        int w0 = (int) Math.floor(((Math.log(q) / Math.log(2)) + 2));
        int r = q - (int) Math.pow(2, w0-2);
        double w1 = r/Math.pow(2, w0-2);
        ECC_Point p1 = Scalar_Multiplication.fractional_wNOF(key.getPublicKey(), key2.getPrivateKey(), w0, w1);
        ECC_Point p2 = Scalar_Multiplication.fractional_wNOF(key2.getPublicKey(), key.getPrivateKey(), w0, w1);
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

    @Test
    public void test_MontgomeryBinary(){
        ECC_Point p1 = Scalar_Multiplication.MontgomeryBinary(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.MontgomeryBinary(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }


    // Test unified schemes
    @Test
    public void test_Unified(){
        ECC_Point p1 = Scalar_Multiplication.binaryNAF_Uni(key.getPublicKey(), key2.getPrivateKey());
        ECC_Point p2 = Scalar_Multiplication.binaryNAF_Uni(key2.getPublicKey(), key.getPrivateKey());
        ECC_Point_Ed_Inv p1_ = ((ECC_Point_Ed_Inv) p1).convertAffine();
        ECC_Point_Ed_Inv p2_ = ((ECC_Point_Ed_Inv) p2).convertAffine();
        assertTrue(p1_.getX().equals(p2_.getX()) && p1_.getY().equals(p2_.getY()));
    }

}
