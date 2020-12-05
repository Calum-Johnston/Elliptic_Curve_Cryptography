package ecc;

import java.math.BigInteger;
import java.util.Arrays;

public class BinaryField_Curve {

    // Equation: y^2 + xy = x^3 + ax^2 + b
    // Random curves B-m, Koblitz curve K-m

    int m; // degree of the field
    BigInteger f;// irreducible polynomial
    BigInteger a; // a coefficient of the equation
    BigInteger b; // b coefficient of the equation
    BinaryField_Point G; // Base point
    BigInteger n; // prime order n of the point G (i.e. n.G = O, where O is point at infinity)
    BigInteger h; // the cofactor of the curve (h = #E(Fp)/n)

    public BinaryField_Curve(int m, String f, String a, String b, String x, String y, String n, String h){
        this.m = m;
        this.f = new BigInteger(f, 16);
        this.a = new BigInteger(a, 16);
        this.b = new BigInteger(b, 16);
        this.G = new BinaryField_Point(this, new BigInteger(x, 16), new BigInteger(y, 16));
        this.n = new BigInteger(n, 16);
        this.h = new BigInteger(h, 16);
    }

    // Addition
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.xor(b);
    }

    // Multiplication
    public BigInteger multiple(BigInteger a, BigInteger b){
        BigInteger mask1 = BigInteger.TWO.pow(m);
        BigInteger mask2 = mask1.subtract(BigInteger.ONE);
        BigInteger p = f.and(mask2);
        BigInteger result = new BigInteger("0");
        for(int i = 0; i < b.bitLength() ; i++){
            if(b.testBit(i)){
                result = result.xor(a);
            }
            a = a.shiftLeft(1);
            if(a.and(mask1).testBit(m)){
                a = a.xor(f);
            }
        }
        return result.and(mask2);
    }

    // Division
    public BigInteger divide(BigInteger a, BigInteger b){
        BigInteger multiplicativeInverse_b = multiplicativeInverse(b);
        BigInteger result = multiple(a, multiplicativeInverse_b);
        return result;
    }

    // Multiplication for MI calculation
    private BigInteger multiple2(BigInteger a, BigInteger b){
        BigInteger result = new BigInteger("0");
        for(int i = 0; i < b.bitLength() ; i++){
            if(b.testBit(i)){
                result = result.xor(a);
            }
            a = a.shiftLeft(1);
        }
        return result;
    }

    // Multiplicative Inverse
    private BigInteger multiplicativeInverse(BigInteger x){
        BigInteger[] row1 = new BigInteger[3];
        BigInteger[] row2 = new BigInteger[3];
        BigInteger[] temp = new BigInteger[3];
        row1[0] = f; row1[1] = BigInteger.ZERO; row1[2] = BigInteger.ONE;
        row2[0] = x; row2[1] = BigInteger.ONE; row2[2] = BigInteger.ZERO;
        temp[0] = BigInteger.ZERO; temp[1] = BigInteger.ZERO; temp[2] = BigInteger.ZERO;
        while(!(temp[0].equals(BigInteger.ONE))) {
            BigInteger q = getQandR(row1[0], row2[0]).getQ();
            temp[0] = row1[0].xor(multiple2(q, row2[0]));
            temp[1] = row1[1].xor(multiple2(q, row2[1]));
            temp[2] = row1[2].xor(multiple2(q, row2[2]));
            row1 = Arrays.copyOf(row2,3);
            row2 = Arrays.copyOf(temp, 3);
        }
        return temp[1];
    }

    // Quotient and remainder
    private QR getQandR(BigInteger a1, BigInteger b1){
        int[] a = new int[a1.bitLength()];
        int[] b = new int[b1.bitLength()];
        String a2 = a1.toString(2);
        String b2 = b1.toString(2);
        for(int i = 0; i < a2.length(); i++){
            a[i] = Character.getNumericValue(a2.charAt(i));
        }
        for(int i = 0; i < b2.length(); i++){
            b[i] = Character.getNumericValue(b2.charAt(i));
        }
        String result = "";
        int aLength = a.length;
        int bLength = b.length;
        int aPointer = 0;
        while(bLength <= aLength && aLength > 0){
            if(a[aPointer] == 1) {
                aPointer++; aLength -= 1;
                for (int j = 0; j < b.length - 1; j++) {
                    a[j + aPointer] = a[j + aPointer] ^ b[j + 1];
                }
                result += "1";
            }
            else{
                aPointer++; aLength -= 1;
                for (int j = 0; j < b.length - 1; j++) {
                    a[j + aPointer] = a[j + aPointer] ^ 0;
                }
                result += "0";
            }
        }
        if (result == "") {
            result ="0";
        }
        BigInteger q = new BigInteger(result, 2);
        String temp = "";
        for(int i = a.length - aLength; i < a.length; i++){
            temp += a[i];
        }
        BigInteger r = new BigInteger(temp, 2);
        return new QR(q, r);
    }


    // Getters and setters
    public int getM(){
        return m;
    }

    public void setM(int m){
        this.m = m;
    }

    public BigInteger getA(){
        return a;
    }

    public void setA(String a){
        this.a = new BigInteger(a, 16);
    }

    public BigInteger getB(){
        return b;
    }

    public void setB(String b){
        this.b = new BigInteger(b, 16);
    }

    public BinaryField_Point getG() {
        return G;
    }

    public void setG(BinaryField_Point g) {
        G = g;
    }

    public BigInteger getN(){
        return n;
    }

    public void setN(String n){
        this.n = new BigInteger(n, 16);
    }

    public BigInteger getH(){
        return h;
    }

    public void setH(String h){
        this.h = new BigInteger(h, 16);
    }
}

