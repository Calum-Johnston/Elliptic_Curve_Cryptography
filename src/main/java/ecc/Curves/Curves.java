package ecc.Curves;

import java.math.BigInteger;

public class Curves {

    // Weierstrass Curves

    // From https://www.ijert.org/research/implementation-of-elliptic-curve-arithmetic-operations-for-prime-field-and-binary-field-using-java-biginteger-class-IJERTV6IS080211.pdf
    public static ECC_Curve_W Weierstrass_192(){
        BigInteger p = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF",16);
        BigInteger a = new BigInteger("-3");
        BigInteger b = new BigInteger("64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1",16);
        BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012",16);
        BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811",16);
        BigInteger n = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831",16);
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_W(p, a, b, x, y, n, h);
    }

    // Potential Curves https://tools.ietf.org/html/rfc5639
    public ECC_Curve_W generatePrimeFieldCurve(){
        BigInteger p = new BigInteger("E95E4A5F737059DC60DFC7AD95B3D8139515620F",16);
        BigInteger a = new BigInteger("340E7BE2A280EB74E2BE61BADA745D97E8F7C300",16);
        BigInteger b = new BigInteger("1E589A8595423412134FAA2DBDEC95C8D8675E58",16);
        BigInteger x = new BigInteger("BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC3",16);
        BigInteger y = new BigInteger("1667CB477A1A8EC338F94741669C976316DA6321",16);
        BigInteger n = new BigInteger("E95E4A5F737059DC60DF5991D45029409E60FC09",16);
        BigInteger h = new BigInteger("1");
        return new ECC_Curve_W(p, a, b, x, y, n, h);
    }

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


}
