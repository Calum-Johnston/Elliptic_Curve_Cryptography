package ecc;

import java.math.BigInteger;

public class ECC_Point {

    ECC_Curve curve;
    BigInteger x;
    BigInteger y;
    boolean infinity;

    public ECC_Point(ECC_Curve curve, BigInteger x, BigInteger y){
        this.curve = curve;
        this.x = x;
        this.y = y;
        infinity = false;
    }

    public ECC_Point(ECC_Curve curve){
        this.curve = curve;
        this.x = new BigInteger("-1");
        this.y = new BigInteger("-1");
        this.infinity = true;
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

    public ECC_Point pointMultiplication(BigInteger n){
        ECC_Point N = this;
        ECC_Point Q = new ECC_Point(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    public ECC_Point pointAddition(ECC_Point point){
        BigInteger gradientNum = point.y.subtract(this.y);
        BigInteger gradientDen = point.x.subtract(this.x).modInverse(curve.p);
        BigInteger grad = gradientNum.multiply(gradientDen).mod(curve.p);
        BigInteger Rx = (grad.pow(2).subtract(this.x).subtract(point.x)).mod(curve.p);
        BigInteger y3 = (((this.x.subtract(Rx)).multiply(grad)).subtract(this.y)).mod(curve.p);
        return new ECC_Point(this.curve, Rx, y3);
    }

    public ECC_Point pointDoubling(){
        BigInteger gradientNum = ((this.x.pow(2)).multiply(new BigInteger("3")).add(curve.a));
        BigInteger gradientDen = this.y.multiply(new BigInteger("2")).modInverse(curve.p);
        BigInteger grad = gradientNum.multiply(gradientDen).mod(curve.p);
        BigInteger Rx = (grad.pow(2).subtract(this.x).subtract(this.x)).mod(curve.p);
        BigInteger Ry = (((x.subtract(Rx)).multiply(grad)).subtract(this.y)).mod(curve.p);
        return new ECC_Point(curve, Rx, Ry);
    }
}
