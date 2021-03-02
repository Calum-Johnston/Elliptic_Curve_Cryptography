package ecc.Weierstrass;

import ecc.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_Aff extends ECC_Point {

    BigInteger x;
    BigInteger y;

    // Constructors

    public ECC_Point_Aff(ECC_Curve_Weierstrass curve, BigInteger x, BigInteger y){
        super(curve, false);
        this.x = x;
        this.y = y;
    }

    public ECC_Point_Aff(ECC_Curve_Weierstrass curve){
        super(curve, true);
    }

    public ECC_Point_Aff(ECC_Point_Proj point){
        super(point.curve, false);
        BigInteger inverseZ = inverse(point.getZ());
        this.x = multiple(point.x, inverseZ);
        this.y = multiple(point.y, inverseZ);
    }

    public ECC_Point_Aff(ECC_Point_Jacob point){
        super(point.curve, false);
        BigInteger invZ = inverse(point.getZ());
        BigInteger invZ_2 = square(invZ);
        BigInteger invZ_3 = multiple(invZ, invZ_2);
        this.x = multiple(point.x, invZ_2);
        this.y = multiple(point.y, invZ_3);
    }

    public ECC_Point_Aff(ECC_Point_Chud point){
        super(point.curve, false);
        BigInteger invZ = inverse(point.getZ());
        BigInteger invZ_2 = square(invZ);
        BigInteger invZ_3 = multiple(invZ, invZ_2);
        this.x = multiple(point.x, invZ_2);
        this.y = multiple(point.y, invZ_3);
    }

    public ECC_Point_Aff(ECC_Point_ModJacob point){
        super(point.curve, false);
        BigInteger invZ = inverse(point.getZ());
        BigInteger invZ_2 = square(invZ);
        BigInteger invZ_3 = multiple(invZ, invZ_2);
        this.x = multiple(point.x, invZ_2);
        this.y = multiple(point.y, invZ_3);
    }



    // Point Multiplication techniques

    //https://www.ijitee.org/wp-content/uploads/papers/v8i6s4/F10360486S419.pdf

    public ECC_Point_Aff doubleAndAdd(BigInteger n){
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

    public ECC_Point_Aff Montgomery(BigInteger n){
        ECC_Point_Aff R0 = new ECC_Point_Aff(this.curve);
        ECC_Point_Aff R1 = this;
        String d = n.toString(2);
        for(int i = 0; i <= d.length() - 1; i++){
            if(d.charAt(i) == '0'){
                R1 = R0.pointAddition(R1);
                R0 = R0.pointDoubling();
            }else{
                R0 = R0.pointAddition(R1);
                R1 = R1.pointDoubling();
            }
        }
        return R0;
    }



    // Point Operations

    public ECC_Point_Aff pointAddition(ECC_Point_Aff point){

        // Perform required checks
        if(this.infinity == true){ // P == O
            return point;
        } else if(point.infinity == true){ // Q == O
            return this;
        } else if(this.x.equals(point.x)) {
            if (this.y.equals(point.y)) {  // P == Q
                return pointDoubling();
            } else {
                return new ECC_Point_Aff(this.curve); // P == -Q (for basic curve)
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
        return new ECC_Point_Aff(this.curve, Rx, Ry);
    }

    public ECC_Point_Aff pointDoubling(){

        // Perform required checks
        if(this.infinity == true) { // P = O
            return this;
        } else if(this.y.equals(BigInteger.ZERO)) { // P == -P
            return new ECC_Point_Aff(this.curve);
        }

        // Computations required
        BigInteger gradNum = add(multiple(new BigInteger("3"), square(this.x)), this.curve.getA());
        BigInteger gradDem = multiple(BigInteger.TWO, this.y);
        BigInteger grad = divide(gradNum, gradDem);

        // Calculate Rx and Ry
        BigInteger Rx = subtract(square(grad), multiple(BigInteger.TWO, this.x));
        BigInteger Ry = subtract(multiple(grad, subtract(this.x, Rx)), this.y);

        // Return the calculated value
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

}
