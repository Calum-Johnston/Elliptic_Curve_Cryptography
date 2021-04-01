package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_xy extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger z;

    // Constructors
    public ECC_Point_W_xy(ECC_Curve_W curve, BigInteger x, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.z = z;
    }

    public ECC_Point_W_xy(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_xy pointDoubling(){

        // Computations required 2M+5S
        BigInteger XX = square(this.x);
        BigInteger ZZ = square(this.z);
        BigInteger A = multiple(BigInteger.TWO, subtract(subtract(square(add(this.x, this.z)), XX), ZZ));
        BigInteger aZZ = multiple(curve.getA(), ZZ);
        BigInteger b4 = multiple(new BigInteger("4"), curve.getB());
        BigInteger x3 = subtract(square(subtract(XX, aZZ)), multiple(b4, multiple(A, ZZ)));
        BigInteger z3 = add(multiple(A, add(XX, aZZ)), multiple(b4, square(ZZ)));

        // Return the calculated value
        return new ECC_Point_W_xy(this.curve, x3, z3);
    }



    // Getters and setters
    public BigInteger getX(){
        return x;
    }

    public void setX(BigInteger x){
        this.x = x;
    }

    public BigInteger getZ() {
        return z;
    }

    public void setZ(BigInteger z) {
        this.z = z;
    }


}
