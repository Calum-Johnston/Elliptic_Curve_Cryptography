package ecc;

import java.math.BigInteger;

public class BinaryField_Point {

    BigInteger x;
    BigInteger y;
    BinaryField_Curve curve;
    boolean infinity;

    public BinaryField_Point(BinaryField_Curve curve, BigInteger x, BigInteger y){
        this.curve = curve;
        this.x = x;
        this.y = y;
        this.infinity = false;
    }

    public BinaryField_Point(BinaryField_Curve curve){
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

    public BinaryField_Point pointMultiplication(BigInteger n){
        BinaryField_Point N = this;
        BinaryField_Point Q = new BinaryField_Point(curve);
        String d = n.toString(2);
        for(int i = d.length() - 1; i >= 0; i--){
            if(d.charAt(i) == '1'){
                Q = Q.pointAddition(N);
            }
            N = N.pointDoubling();
        }
        return Q;
    }

    // https://www.certicom.com/content/certicom/en/42-arithmetic-in-an-elliptic-curve-group-over-f2m.html
    public BinaryField_Point pointAddition(BinaryField_Point point){
        if(this.x.compareTo(point.x) != 0 && this.y.compareTo(point.y) != 0){  // P != Q
            if(this.x.compareTo(point.x) == 0 && this.y.compareTo(curve.add(point.x, point.y)) == 0){ // P = -Q
                return new BinaryField_Point(curve);
            } else if(this.infinity == true){ // P = O
                return point;
            } else if(point.infinity == true){ // Q = 0
                return this;
            } else {
                BigInteger gradientNum = curve.add(this.y, point.y);
                BigInteger gradientDen = curve.add(this.x, point.x);
                BigInteger grad = curve.divide(gradientNum, gradientDen);
                BigInteger Rx = curve.add(curve.multiple(grad, grad), curve.add(grad, curve.add(this.x, curve.add(point.x, curve.a))));
                BigInteger Ry = curve.add(curve.multiple(grad, curve.add(Rx, this.x)), curve.add(Rx, this.y));
                return new BinaryField_Point(this.curve, Rx, Ry);
            }
        }
        return pointDoubling();
    }

    // https://www.certicom.com/content/certicom/en/42-arithmetic-in-an-elliptic-curve-group-over-f2m.html
    public BinaryField_Point pointDoubling(){
        if(this.y.compareTo(this.curve.add(this.x, this.y)) == 0){ // P = -P
            return new BinaryField_Point(curve);
        }
        if(this.infinity == true){ // P = O
            return new BinaryField_Point(curve);
        }
        BigInteger gradientDiv = curve.divide(this.y, this.x);
        BigInteger grad = curve.add(this.x, gradientDiv);
        BigInteger Rx = curve.add(curve.multiple(grad, grad) , curve.add(grad, curve.a));
        BigInteger Ry = curve.add(curve.multiple(this.x, this.x), curve.multiple(curve.add(grad, BigInteger.ONE), Rx));
        return new BinaryField_Point(this.curve, Rx, Ry);
    }
}
