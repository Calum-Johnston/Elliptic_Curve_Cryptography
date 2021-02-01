import ecc.*;

import java.math.BigInteger;

public class Main {

    // Potential Curves https://tools.ietf.org/html/rfc5639
    public ECC_Curve generatePrimeFieldCurve(){
        String p = "E95E4A5F737059DC60DFC7AD95B3D8139515620F";
        String a = "340E7BE2A280EB74E2BE61BADA745D97E8F7C300";
        String b = "1E589A8595423412134FAA2DBDEC95C8D8675E58";
        String x = "BED5AF16EA3F6A4F62938C4631EB5AF7BDBCDBC3";
        String y = "1667CB477A1A8EC338F94741669C976316DA6321";
        String n = "E95E4A5F737059DC60DF5991D45029409E60FC09";
        String h = "1";
        return new ECC_Curve(p, a, b, x, y, n, h);
    }

    // From https://www.ijert.org/research/implementation-of-elliptic-curve-arithmetic-operations-for-prime-field-and-binary-field-using-java-biginteger-class-IJERTV6IS080211.pdf
    public ECC_Curve generatePrimeFieldCurve_192(){
        String p = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFFFFFFFFFFFFF";
        String a = "-3";
        String b = "64210519e59c80e70fa7e9ab72243049feb8deecc146b9b1";
        String x = "188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012";
        String y = "07192b95ffc8da78631011ed6b24cdd573f977a11e794811";
        String n = "FFFFFFFFFFFFFFFFFFFFFFFF99DEF836146BC9B1B4D22831";
        String h = "1";
        return new ECC_Curve(p, a, b, x, y, n, h, true);
    }

    public ECC_Curve generatePrimeFieldCurve_basic(){
        String p = "17";
        String a = "1";
        String b = "1";
        String x = "3";
        String y = "A";
        String n = "1C";
        String h = "1";
        return new ECC_Curve(p, a, b, x, y, n, h);
    }

    public void test(){
        ECC_Curve curve = generatePrimeFieldCurve();
        BigInteger multiple = new BigInteger("3");

        // Affine
        ECC_Point_Aff affine = curve.getG();
        ECC_Point_Aff temp = affine.pointMultiplication(multiple);
        BigInteger x = temp.getX();
        BigInteger y = temp.getY();

        // Projective
        ECC_Point_Proj proj = new ECC_Point_Proj(curve, affine.getX(), affine.getY(), BigInteger.ONE);
        ECC_Point_Proj temp2 = proj.pointMultiplication(multiple);
        BigInteger z_ = temp2.getZ().modInverse(curve.getP());
        BigInteger x1 = temp2.getX().multiply(z_).mod(curve.getP());
        BigInteger y1 = temp2.getY().multiply(z_).mod(curve.getP());

        // Jacobian
        ECC_Point_Jacob jac = new ECC_Point_Jacob(curve, affine.getX(), affine.getY(), BigInteger.ONE);
        ECC_Point_Jacob temp3 = jac.pointMultiplication(multiple);
        BigInteger z2 = temp3.getZ().pow(2).mod(curve.getP());
        BigInteger z3 = temp3.getZ().pow(3).mod(curve.getP());
        BigInteger z2_ = z2.modInverse(curve.getP());
        BigInteger z3_ = z3.modInverse(curve.getP());
        BigInteger x2 = temp3.getX().multiply(z2_).mod(curve.getP());
        BigInteger y2 = temp3.getY().multiply(z3_).mod(curve.getP());

        // Chudnovsky
        ECC_Point_Chud chud = new ECC_Point_Chud(curve, affine.getX(), affine.getY(), BigInteger.ONE,
                                BigInteger.ONE, BigInteger.ONE);
        ECC_Point_Chud temp4 = chud.pointMultiplication(multiple);
        z2 = temp4.getZ2().mod(curve.getP());
        z3 = temp4.getZ3().mod(curve.getP());
        z2_ = z2.modInverse(curve.getP());
        z3_ = z3.modInverse(curve.getP());
        BigInteger x3 = temp4.getX().multiply(z2_).mod(curve.getP());
        BigInteger y3 = temp4.getY().multiply(z3_).mod(curve.getP());

        boolean projective;
        boolean jacobian;
        boolean chudnovsky;
        if(x.equals(x1) && y.equals(y1)){
            projective = true;
        }else{
            projective = false;
        }
        if(x.equals(x2) && y.equals(y2)){
            jacobian = true;
        }else{
            jacobian = false;
        }
        if(x.equals(x3) && y.equals(y3)){
            chudnovsky = true;
        }else {
            chudnovsky = false;
        }
        System.out.println("Projective: " + projective);
        System.out.println("Jacobian: " + jacobian);
        System.out.println("Chudnovsky: " + chudnovsky);
    }

    public void test_PrimeFieldCurve(){
        String message = "Alisha Jane Walsh";
        ECC_Curve curve = generatePrimeFieldCurve();

        // Generate key pair
        ECC_Key k1 = new ECC_Key(curve);
        ECC_Key k2 = new ECC_Key(curve);

        // ECDSA
        ECC_Signature sig = ECDSA.Sign(curve, k1, message);
        Boolean valid = ECDSA.Verify(curve, sig, k1, message);
        System.out.println("Prime field ECDSA: " + valid);

        // ECDH
        BigInteger sharedSecret = ECDH.computeSecret(curve, k1.getPublicKey(), k2.getPrivateKey());
        BigInteger sharedSecret2 = ECDH.computeSecret(curve, k2.getPublicKey(), k1.getPrivateKey());
        System.out.println("Prime field ECDH: " + sharedSecret.equals(sharedSecret2));
    }

    public static void main(String[] args){
        Main main = new Main();
        main.test();
    }

}
