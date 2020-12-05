package ecc;

import java.math.BigInteger;

public class BinaryField_Point {

    BigInteger x;
    BigInteger y;
    BinaryField_Curve curve;

    public BinaryField_Point(BinaryField_Curve curve, BigInteger x, BigInteger y){
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

    public BinaryField_Point pointMultiplication(BigInteger n){
        BinaryField_Point N = this;
        BinaryField_Point Q = new BinaryField_Point(null,null, null);
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
        if(this.x == null){ // Point represents infinity
            return point;
        }
        BigInteger gradientNum = curve.add(this.y, point.y); // Py - Qy
        BigInteger gradientDen = curve.add(this.x, point.x); // Px - Qx
        BigInteger grad = curve.divide(gradientNum, gradientDen);
        BigInteger Rx = curve.add(curve.multiple(grad, grad) ,curve.add(grad, curve.add(this.x , curve.add(point.x, curve.a))));
        BigInteger Ry = curve.add(curve.multiple(grad, curve.add(Rx, this.x)), curve.add(Rx, this.y));
        return new BinaryField_Point(this.curve, Rx, Ry);
    }

    // https://www.certicom.com/content/certicom/en/42-arithmetic-in-an-elliptic-curve-group-over-f2m.html
    public BinaryField_Point pointDoubling(){
        BigInteger gradientNum = curve.add(this.x, this.y);
        BigInteger grad = curve.divide(gradientNum, this.x);
        BigInteger Rx = curve.add(curve.multiple(grad, grad) , curve.add(grad, curve.a));
        BigInteger Ry = curve.add(curve.multiple(this.x, this.x), curve.multiple(curve.add(grad, BigInteger.ONE), Rx));
        return new BinaryField_Point(this.curve, Rx, Ry);
    }
}
