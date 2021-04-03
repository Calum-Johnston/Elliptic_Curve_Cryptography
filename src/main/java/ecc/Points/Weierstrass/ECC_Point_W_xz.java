package ecc.Points.Weierstrass;

import ecc.Curves.ECC_Curve_W;
import ecc.Points.ECC_Point;

import java.math.BigInteger;

public class ECC_Point_W_xz extends ECC_Point {

    ECC_Curve_W curve;
    BigInteger x;
    BigInteger z;

    // Constructors
    public ECC_Point_W_xz(ECC_Curve_W curve, BigInteger x, BigInteger z){
        super(false, curve.getP());
        this.curve = curve;
        this.x = x;
        this.z = z;
    }

    public ECC_Point_W_xz(ECC_Curve_W curve){
        super(true, null);
        this.curve = curve;
    }



    // Point Operations
    public ECC_Point_W_xz pointDoubling(){

        // Computations required 2M+5S
        BigInteger XX = square(this.x);
        BigInteger ZZ = square(this.z);
        BigInteger A = multiple(BigInteger.TWO, subtract(subtract(square(add(this.x, this.z)), XX), ZZ));
        BigInteger aZZ = multiple(curve.getA(), ZZ);
        BigInteger b2 = multiple(BigInteger.TWO, curve.getB());
        BigInteger b4 = multiple(BigInteger.TWO, b2);
        BigInteger x3 = subtract(square(subtract(XX, aZZ)), multiple(b2, multiple(A, ZZ)));
        BigInteger z3 = add(multiple(A, add(XX, aZZ)), multiple(b4, square(ZZ)));

        // Return the calculated value
        return new ECC_Point_W_xz(this.curve, x3, z3);
    }

    public ECC_Point_W_xz diffAddition(ECC_Point p, ECC_Point d){

        // Convert point
        ECC_Point_W_xz point = (ECC_Point_W_xz) p;
        ECC_Point_W_xz diff = (ECC_Point_W_xz) d;

        // Computations required 7M+2S+2D
        BigInteger T1 = multiple(this.x, point.x);
        BigInteger T2 = multiple(this.z, point.z);
        BigInteger T3 = multiple(this.x, point.z);
        BigInteger T4 = multiple(this.z, point.x);
        BigInteger T5 = multiple(curve.getA(), T2);
        BigInteger T6 = subtract(T1, T5);
        BigInteger T7 = square(T6);
        BigInteger T8 = multiple(curve.getB(), T2);
        BigInteger T9 = multiple(new BigInteger("4"), T8);
        BigInteger T10 = add(T3, T4);
        BigInteger T11 = multiple(T9, T10);
        BigInteger T12 = subtract(T7, T11);
        BigInteger x4 = multiple(diff.z, T12);
        BigInteger T13 = subtract(T3, T4);
        BigInteger T14 = square(T13);
        BigInteger z4 = multiple(diff.x, T14);

        // Return the calculated value
        return new ECC_Point_W_xz(this.curve, x4, z4);

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
