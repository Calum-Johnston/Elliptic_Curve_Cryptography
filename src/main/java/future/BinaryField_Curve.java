package future;

import java.math.BigDecimal;
import java.math.BigInteger;

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

    /**
     * Either have a prime field or non prime field :)
     */

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
        BigInteger temp = a.xor(b);
        if(temp.bitLength() >= f.bitLength()){
            return reduce(temp, this.f);
        }
        return temp;
    }

    // Multiplication
    public BigInteger multiple(BigInteger a, BigInteger b){
        BigInteger mask1 = BigInteger.TWO.pow(m);
        BigInteger mask2 = mask1.subtract(BigInteger.ONE);
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

    // Reduce by f(x)
    public BigInteger reduce(BigInteger a, BigInteger b){
        int shift = a.bitLength() - b.bitLength();
        b = b.shiftLeft(shift);
        int currentBit = a.bitLength() - 1;
        for(int i = 0; i < shift + 1; i++){
            if(a.testBit(currentBit)){
                a = a.xor(b);
            }
            b = b.shiftRight(1);
            currentBit --;
        }
        return a;
    }


    // Division
    public BigInteger divide(BigInteger a, BigInteger b){
        BigInteger multiplicativeInverse_b = multiplicativeInverse(b);
        BigInteger result = multiple(a, multiplicativeInverse_b);
        return result;
    }

    // Multiplicative Inverse
    // file:///C:/Users/CALUMJ~1/AppData/Local/Temp/Guide%20to%20Elliptic%20Curve%20Cryptography%20(%20PDFDrive%20).pdf
    public BigInteger multiplicativeInverse(BigInteger x){
        BigInteger u = x;
        BigInteger v = this.f;
        BigInteger g1 = BigInteger.ONE;
        BigInteger g2 = BigInteger.ZERO;
        while(!u.equals(BigInteger.ONE)){
            int j = degree(u) - degree(v);
            if(j < 0){
                BigInteger temp;
                temp = u;
                u = v;
                v = temp;
                temp = g1;
                g1 = g2;
                g2 = temp;
                j = -j;
            }
            BigInteger poly = BigDecimal.valueOf(Math.pow(2, j)).toBigInteger();
            u = add(u, multiple(poly, v));
            g1 = add(g1, multiple(poly, g2));
        }
        return g1;
    }

    public int degree(BigInteger x){
        String temp = x.toString(2);
        return temp.length() - 1;
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

