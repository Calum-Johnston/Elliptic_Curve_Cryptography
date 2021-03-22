package ecc.Doche_Icart_Kohel;

import ecc.Curves.ECC_Curve_DIK3;
import ecc.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_DIK3 extends ECC_Point {

    ECC_Curve_DIK3 curve;
    BigInteger x;
    BigInteger y;

    // Constructors
    public ECC_Point_DIK3(ECC_Curve_DIK3 curve, BigInteger x, BigInteger y){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.y = y;
    }

    public ECC_Point_DIK3(ECC_Curve_DIK3 curve){
        super(true, null);
        this.curve = curve;
    }


    // Point Operations
    public ECC_Point_DIK3 pointAddition(ECC_Point p){

        ECC_Point_DIK3 point = (ECC_Point_DIK3) p;

        // Perform required checks
        if(isInfinity() == true){ // P == O
            return point;
        } else if(point.isInfinity() == true){ // Q == O
            return this;
        } else if(this.x.equals(point.x)) {
            if (this.y.equals(point.y)) {  // P == Q
                return pointDoubling();
            } else {
                return new ECC_Point_DIK3(this.curve); // P == -Q (for basic curve)
            }
        }

        // Computations required
        BigInteger gradNum = subtract(point.y, this.y);
        BigInteger gradDem = subtract(point.x, this.x);
        BigInteger grad = divide(gradNum, gradDem);

        // Calculate Rx and Ry
        BigInteger Rx = subtract(subtract(square(grad), this.x), point.x);
        BigInteger Ry = subtract(multiple(grad, subtract(this.x, Rx)), this.y);

        // Return the calculated value
        return new ECC_Point_DIK3(this.curve, Rx, Ry);
    }

    public ECC_Point_DIK3 pointDoubling(){

        // Perform required checks
        if(isInfinity() == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_DIK3(this.curve);
        }

        // Computations required
        BigInteger gradNum = add(multiple(new BigInteger("3"), square(this.x)), this.curve.getA());
        BigInteger gradDem = multiple(BigInteger.TWO, this.y);
        BigInteger grad = divide(gradNum, gradDem);

        // Calculate Rx and Ry
        BigInteger Rx = subtract(square(grad), multiple(BigInteger.TWO, this.x));
        BigInteger Ry = subtract(multiple(grad, subtract(this.x, Rx)), this.y);

        // Return the calculated value
        return new ECC_Point_DIK3(curve, Rx, Ry);
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

}
