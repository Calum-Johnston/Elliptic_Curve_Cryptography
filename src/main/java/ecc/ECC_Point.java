package ecc;

import java.math.BigInteger;

public class ECC_Point {

    ECC_Curve curve;
    BigInteger x;
    BigInteger y;

    public ECC_Point(ECC_Curve curve, BigInteger x, BigInteger y){
        this.curve = curve;
        this.x = x;
        this.y = y;
    }

    public BigInteger getX(){
        return x;
    }

    public BigInteger getY(){
        return y;
    }

    public ECC_Curve getCurve(){
        return curve;
    }

    public ECC_Point pointMultiplication(BigInteger n, BigInteger p, BigInteger a){
        ECC_Point N = this;
        ECC_Point Q = new ECC_Point(this.curve,null, null);
        boolean infinity = true;
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                if(infinity == true){
                    infinity = false;
                    Q = N;
                }else {
                    Q = Q.pointAddition(N.x, N.y, p);
                }
            }
            N = N.pointDoubling(a, p);
        }
        return Q;
    }

    public ECC_Point pointAddition(BigInteger x2, BigInteger y2, BigInteger p){
        BigInteger gradientNumerator = y2.subtract(y);
        BigInteger gradientDenominator = (x2.subtract(x)).modInverse(p);
        BigInteger gradient = gradientNumerator.multiply(gradientDenominator).mod(p);
        BigInteger x3 = (gradient.pow(2).subtract(x).subtract(x2)).mod(p);
        BigInteger y3 = (((x.subtract(x3)).multiply(gradient)).subtract(y)).mod(p);
        return new ECC_Point(curve, x3, y3);
    }

    public ECC_Point pointDoubling(BigInteger a, BigInteger p){
        BigInteger gradientNumerator = ((x.pow(2)).multiply(new BigInteger("3")).add(a));
        BigInteger gradientDenominator = y.multiply(new BigInteger("2")).modInverse(p);
        BigInteger gradient = gradientNumerator.multiply(gradientDenominator).mod(p);
        BigInteger x3 = (gradient.pow(2).subtract(x).subtract(x)).mod(p);
        BigInteger y3 = (((x.subtract(x3)).multiply(gradient)).subtract(y)).mod(p);
        return new ECC_Point(curve, x3, y3);
    }
}
