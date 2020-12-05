import ecc.*;
import rsa.RSA;
import rsa.RSA_Key;

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
        BigInteger p = new BigInteger("800000000000000000000000000000000000000C9", 16);
        BigInteger m = new BigInteger("2fe13c0537bbc11acaa07d793de4e6d5e5c94eee8", 16);
        BigInteger n = new BigInteger("289070fb05d38ff58321f2e800536d538ccdaa3d9", 16);
        int degree = 163;
        BigInteger addition2 = new BigInteger("7714cfe32684eef49818f913db78b866904e4d31", 16);
        BigInteger subtraction2 = new BigInteger("7714cfe32684eef49818f913db78b866904e4d31", 16);
        BigInteger multiplication2 = new BigInteger("4d741872162b253d5a381f1f680b47e5c0ad3aa2a", 16);
        BigInteger division2 = new BigInteger("498d03bb544d83614e0b5963052f604eb8ec8d0cd", 16);
        BigInteger multiplicativeInverse2 = new BigInteger("63f514f39f4587684f96c8dd6558e69339a1efed9", 16);
        System.out.println("Binary finite field:");
        System.out.println("Addition: " + (m.xor(n)).equals(addition2));
        System.out.println("Subtraction: " + (m.xor(n)).equals(subtraction2));
        System.out.println("Multiplication: " + (BinaryField_Operations.multiple(m, n, p, degree)).equals(multiplication2));
        System.out.println("Division: " + BinaryField_Operations.division(m,n,p, degree).equals(division2));
        System.out.println("Multiplicative Inverse: " + BinaryField_Operations.multiplicativeInverse(m,p, degree).equals(multiplicativeInverse2));
    }

    public void test_PrimeFieldCurve(){
        String message = "Alisha Jane Walsh";
        ECC_Curve curve = generatePrimeFieldCurve();
        ECC_Key keyPairECC = new ECC_Key(curve);
        ECC_Signature sig = ECDSA.Sign(curve, keyPairECC, message);
        Boolean valid = ECDSA.Verify(curve, sig, keyPairECC, message);
        System.out.println(valid);

        ECC_Key keyPairECC2 = new ECC_Key(curve);
        BigInteger sharedSecret = ECDH.computeSecret(curve, keyPairECC.getPublicKey(), keyPairECC2.getPrivateKey());
        BigInteger sharedSecret2 = ECDH.computeSecret(curve, keyPairECC2.getPublicKey(), keyPairECC.getPrivateKey());
        System.out.println(sharedSecret.equals(sharedSecret2));

        RSA_Key keyPairRSA = new RSA_Key(2048);
        BigInteger s = RSA.Sign(message, keyPairRSA.getPrivateKey(), keyPairRSA.getN());
        Boolean valid2 = RSA.Verify(message, s, keyPairRSA.getPublicKey(), keyPairRSA.getN());
        System.out.println(valid);
        return;
    }

    public void test_BinaryFieldCurve(){
        String message = "Alisha Jane Walsh";
        BinaryField_Curve  curve = generateBinaryFieldCurve();
        BinaryField_Point point = new BinaryField_Point(curve, new BigInteger("1001", 2), new BigInteger("1001", 2));
        point.pointMultiplication(BigInteger.TWO);

        // Generate key pair
        BinaryECC_Key k1 = new BinaryECC_Key(curve);
        BinaryECC_Key k2 = new BinaryECC_Key(curve);

        BinaryField_Point sharedK = k1.getPublicKey().pointMultiplication(k2.getPrivateKey());
        BinaryField_Point sharedK1 = k2.getPublicKey().pointMultiplication(k1.getPrivateKey());

        // One of Point addition, Point doubling and key generation doesn't work currently
        // Need to identify which one

        int a = 2;
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
        int degree = 113;
        String f = "80000000000000000000000201";
        String a = "003088250CA6E7C7FE649CE85820F7";
        String b = "00E8BEE4D3E2260744188BE0E9C723";
        String x = "04009D73616F35F4AB1407D73562C1";
        String y = "0F00A52830277958EE84D1315ED31886";
        String n = "0100000000000000D9CCEC8A39E56F";
        String h = "2";
        return new BinaryField_Curve(degree, f, a, b, x, y, n, h);
    }

    public static void main(String[] args){
        Main main = new Main();
        main.test_BinaryFieldCurve();


    }

}
