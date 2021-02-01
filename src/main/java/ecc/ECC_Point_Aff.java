package ecc;

import java.math.BigInteger;

public class ECC_Point_Aff extends ECC_Point {

    BigInteger x;
    BigInteger y;

    public ECC_Point_Aff(ECC_Curve curve, BigInteger x, BigInteger y){
        super(curve, false);
        this.x = x;
        this.y = y;
    }

    public ECC_Point_Aff(ECC_Curve curve){
        super(curve, true);
        this.x = new BigInteger("-1");
        this.y = new BigInteger("-1");
    }

    public ECC_Point_Aff pointMultiplication(BigInteger n){
        ECC_Point_Aff N = this;
        ECC_Point_Aff Q = new ECC_Point_Aff(this.curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    public ECC_Point_Aff pointAddition(ECC_Point_Aff point){
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        } else if(this.x.equals(point.x) && this.y.equals(point.y)) {  // P == Q
            return pointDoubling();
        } else if(this.x.equals(point.x) && this.y.equals(point.y.negate())) { // P == -Q
            return new ECC_Point_Aff(this.curve);
        }
        BigInteger gradientNum = point.y.subtract(this.y);
        BigInteger gradientDen = point.x.subtract(this.x).modInverse(curve.p);
        BigInteger grad = gradientNum.multiply(gradientDen).mod(curve.p);
        BigInteger Rx = (grad.pow(2).subtract(this.x).subtract(point.x)).mod(curve.p);
        BigInteger y3 = (((this.x.subtract(Rx)).multiply(grad)).subtract(this.y)).mod(curve.p);
        return new ECC_Point_Aff(this.curve, Rx, y3);
    }

    public ECC_Point_Aff pointDoubling(){
        if(this.y.equals(this.y.negate())) { // P == -P
            return new ECC_Point_Aff(this.curve);
        }
        if(this.infinity == true){ // P = O
            return this;
        }
        BigInteger gradientNum = ((this.x.pow(2)).multiply(new BigInteger("3")).add(curve.a));
        BigInteger gradientDen = this.y.multiply(new BigInteger("2")).modInverse(curve.p);
        BigInteger grad = gradientNum.multiply(gradientDen).mod(curve.p);
        BigInteger Rx = (grad.pow(2).subtract(this.x).subtract(this.x)).mod(curve.p);
        BigInteger Ry = (((x.subtract(Rx)).multiply(grad)).subtract(this.y)).mod(curve.p);
        return new ECC_Point_Aff(curve, Rx, Ry);
    }


    // Getters and setters

    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getY(){
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public ECC_Curve getCurve(){
        return curve;
    }

    public void setCurve(ECC_Curve curve){
        this.curve = curve;
    }
}
