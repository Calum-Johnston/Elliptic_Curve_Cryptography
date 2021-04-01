package ecc.Curves;

import java.math.BigInteger;

public class Curves {

    public static ECC_Curve_W generatePrimeFieldCurve_basic(){
        BigInteger p = new BigInteger("23");
        BigInteger a = new BigInteger("1");
        BigInteger b = new BigInteger("1");
        BigInteger x = new BigInteger("3");
        BigInteger y = new BigInteger("10");
        BigInteger n = new BigInteger("24");
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_W(p, a, b, x, y, n, h);
    }

    // Weierstrass curve
    // a = -1
    public static ECC_Curve_W secp256r1(){
        BigInteger p = new BigInteger("ffffffff00000001000000000000000000000000ffffffffffffffffffffffff", 16);
        BigInteger a = new BigInteger("ffffffff00000001000000000000000000000000fffffffffffffffffffffffc", 16);
        BigInteger b = new BigInteger("5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b", 16);
        BigInteger x = new BigInteger("6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296", 16);
        BigInteger y = new BigInteger("4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5", 16);
        BigInteger n = new BigInteger("ffffffff00000000ffffffffffffffffbce6faada7179e84f3b9cac2fc632551", 16);
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_W(p, a, b, x, y, n, h);
    }

    // Montgomery curve
    public static ECC_Curve_Montgomery Curve25519(){
        BigInteger p = new BigInteger("7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffed", 16);
        BigInteger a = new BigInteger("76d06", 16);
        BigInteger b = new BigInteger("01", 16);
        BigInteger x = new BigInteger("09", 16);
        BigInteger y = new BigInteger("20ae19a1b8a086b4e01edd2c7748d14c923d4d7e6d7c61b229e9c5a27eced3d9", 16);
        BigInteger n = new BigInteger("1000000000000000000000000000000014def9dea2f79cd65812631a5cf5d3ed", 16);
        BigInteger h = new BigInteger("08");
        return new ECC_Curve_Montgomery(p, a, b, x, y, n, h);
    }

    // Edwards Curve
    // https://link.springer.com/chapter/10.1007/978-3-319-59870-3_19
    public static ECC_Curve_Ed Ed25519(){
        BigInteger p = BigInteger.TWO.pow(256).subtract(new BigInteger("90437671211985546874316358605566976675"));
        BigInteger d = new BigInteger("2232957402930888962690153848203737819511656455704365203613889338415517544966");
        BigInteger x = new BigInteger("38827631816508813273841327893835794280440756912139095629484251847080307749332");
        BigInteger y = new BigInteger("45829116834518642034200069292195310273647442070782884666574460125768495421042");
        BigInteger n = new BigInteger("28948022309329048855892746252171976963294556678555688323048256808032754388119");
        BigInteger h = new BigInteger("4");
        return new ECC_Curve_Ed(p, BigInteger.ONE, d, x, y, n, h);
    }

    // Twisted Edwards Curve https://neuromancer.sk/std/other/Ed25519
    // a = -1
    public static ECC_Curve_Ed TwEd25519(){
        BigInteger p = new BigInteger("7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffed", 16);
        BigInteger a = new BigInteger("-1");
        BigInteger d = new BigInteger("52036cee2b6ffe738cc740797779e89800700a4d4141d8ab75eb4dca135978a3", 16);
        BigInteger x = new BigInteger("216936D3CD6E53FEC0A4E231FDD6DC5C692CC7609525A7B2C9562D608F25D51A", 16);
        BigInteger y = new BigInteger("6666666666666666666666666666666666666666666666666666666666666658", 16);
        BigInteger n = new BigInteger("1000000000000000000000000000000014def9dea2f79cd65812631a5cf5d3ed", 16);
        BigInteger h = new BigInteger("08", 16);
        return new ECC_Curve_Ed(p, a, d, x, y, n, h);
    }

}
