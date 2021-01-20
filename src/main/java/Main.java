import ecc.*;
import future.BinaryECC_Key;
import future.BinaryField_Curve;
import future.BinaryField_Point;

import java.math.BigInteger;

public class Main {

    public void test_Operations(){
        // Testing for prime finite field arithmetic
        BigInteger prime = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
        BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
        BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
        BigInteger addition = new BigInteger("776096614669310688163071032867745522280722465564271335459");
        BigInteger subtraction = new BigInteger("427995950082066625353355928307306701552675487709498034177");
        BigInteger multiplication = new BigInteger("4639807044776303443638933838541143505414608422678862314472");
        BigInteger division = new BigInteger("1020231484063268998397851297726572901286284127207709149774");
        System.out.println("Prime finite field:");
        System.out.println("Addition: " + (x.add(y)).equals(addition));
        System.out.println("Subtraction: " + (x.subtract(y)).equals(subtraction));
        System.out.println("Multiplication: " + (x.multiply(y).mod(prime)).equals(multiplication));
        System.out.println("Division: " + (x.multiply(y.modInverse(prime))).mod(prime).equals(division));

        // Testing for binary finite field arithmetic
        BinaryField_Curve curve = generateBinaryFieldCurve();
        BinaryField_Point point = curve.getG();
        BigInteger addition2 = new BigInteger("7714cfe32684eef49818f913db78b866904e4d31", 16);
        BigInteger subtraction2 = new BigInteger("7714cfe32684eef49818f913db78b866904e4d31", 16);
        BigInteger multiplication2 = new BigInteger("4d741872162b253d5a381f1f680b47e5c0ad3aa2a", 16);
        BigInteger division2 = new BigInteger("498d03bb544d83614e0b5963052f604eb8ec8d0cd", 16);
        BigInteger multiplicativeInverse2 = new BigInteger("63f514f39f4587684f96c8dd6558e69339a1efed9", 16);
        System.out.println("Binary finite field:");
        System.out.println("Addition: " + (curve.add(point.getX(), point.getY()).equals(addition2)));
        System.out.println("Subtraction: " + (curve.add(point.getX(), point.getY())).equals(subtraction2));
        System.out.println("Multiplication: " + (curve.multiple(point.getX(), point.getY())).equals(multiplication2));
        System.out.println("Division: " + curve.divide(point.getX(), point.getY()).equals(division2));
        System.out.println("Multiplicative Inverse: " + curve.multiplicativeInverse(point.getX()).equals(multiplicativeInverse2));
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

    public void test_BinaryFieldCurve(){
        String message = "Alisha Jane Walsh";
        BinaryField_Curve curve = generateBinaryFieldCurve();

        // Generate key pair
        BinaryECC_Key k1 = new BinaryECC_Key(curve);
        BinaryECC_Key k2 = new BinaryECC_Key(curve);

        // ECDSA (doesn't work because of mod n!)
        ECC_Signature sig = ECDSA.Sign(curve, k1, message);
        Boolean valid = ECDSA.Verify(curve, sig, k1, message);
        System.out.println("Binary field ECDSA: " + valid);

        // ECDH
        BigInteger sharedSecret = ECDH.computeSecret(curve, k1.getPublicKey(), k2.getPrivateKey());
        BigInteger sharedSecret2 = ECDH.computeSecret(curve, k2.getPublicKey(), k1.getPrivateKey());
        System.out.println("Binary field ECDH: " + sharedSecret.equals(sharedSecret2));
    }

    public void compareSpeeds(){
        long startTime;
        long endTime;
        long duration;

        // Initialise
        BigInteger prime = new BigInteger("6277101735386680763835789423207666416083908700390324961279");
        BigInteger x = new BigInteger("188da80eb03090f67cbf20eb43a18800f4ff0afd82ff1012", 16);
        BigInteger y = new BigInteger("07192b95ffc8da78631011ed6b24cdd573f977a11e794811", 16);
        BinaryField_Curve curve = generateBinaryFieldCurve();
        BinaryField_Point point = curve.getG();

        // Addition
        startTime = System.nanoTime();
        x.subtract(y).mod(prime);
        endTime = System.nanoTime();
        System.out.println("Prime subtraction: " + (endTime - startTime));
        startTime = System.nanoTime();
        curve.add(point.getX(), point.getY());
        endTime = System.nanoTime();
        System.out.println("Binary subtraction: " + (endTime - startTime));

        // Subtraction
        startTime = System.nanoTime();
        x.add(y).mod(prime);
        endTime = System.nanoTime();
        System.out.println("Prime addition: " + (endTime - startTime));
        startTime = System.nanoTime();
        curve.add(point.getX(), point.getY());
        endTime = System.nanoTime();
        System.out.println("Binary addition: " + (endTime - startTime));

        // Multiplication
        startTime = System.nanoTime();
        x.multiply(y).mod(prime);
        endTime = System.nanoTime();
        System.out.println("Prime multiplication: " + (endTime - startTime));
        startTime = System.nanoTime();
        curve.add(point.getX(), point.getY());
        endTime = System.nanoTime();
        System.out.println("Binary multiplication: " + (endTime - startTime));

        // Division
        startTime = System.nanoTime();
        x.multiply(y.modInverse(prime)).mod(prime);
        endTime = System.nanoTime();
        System.out.println("Prime division: " + (endTime - startTime));
        startTime = System.nanoTime();
        curve.divide(point.getX(), point.getY());
        endTime = System.nanoTime();
        System.out.println("Binary division: " + (endTime - startTime));

        // MI
        startTime = System.nanoTime();
        y.modInverse(prime);
        System.out.println(y);
        endTime = System.nanoTime();
        System.out.println("Prime MI: " + (endTime - startTime));
        startTime = System.nanoTime();
        curve.multiplicativeInverse(point.getX());
        System.out.println(point.getX());
        endTime = System.nanoTime();
        System.out.println("Binary MI: " + (endTime - startTime));

    }

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

    // Potential curves: https://www.secg.org/SEC2-Ver-1.0.pdf
    public BinaryField_Curve generateBinaryFieldCurve(){
        int degree = 163;
        String f = "800000000000000000000000000000000000000C9";
        String a = "1";
        String b = "1";
        String x = "2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8";
        String y = "289070fb05d38ff58321f2e800536d538ccdaa3d9";
        String n = "4000000000000000000020108A2E0CC0D99F8A5EF";
        String h = "2";
        return new BinaryField_Curve(degree, f, a, b, x, y, n, h);
    }

    public BinaryField_Curve generateBinaryFieldCurve2(){
        int degree = 4;
        String f = "13";
        String a = "3";
        String b = "1";
        String x = "F";
        String y = "F";
        String n = "10";
        String h = "1";

        // 16 points on the curve
        // n = h * l
        // l = order of generator
        // h = cofactor

        return new BinaryField_Curve(degree, f, a, b, x, y, n, h);
    }



    public static void main(String[] args){
        Main main = new Main();
        main.test_Operations();
        main.test_BinaryFieldCurve();
        main.test_PrimeFieldCurve();
    }

}
